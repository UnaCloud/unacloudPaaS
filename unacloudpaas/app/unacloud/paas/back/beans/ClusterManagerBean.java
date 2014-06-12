package unacloud.paas.back.beans;

import com.avaje.ebean.Ebean;
import models.*;
import models.enums.ExecutionState;
import models.enums.ResourceType;
import unacloud.paas.back.execution.RuntimePlatformExecutionBean;
import unacloud.paas.back.iaasservices.ClusterServices;
import unacloud.paas.back.iaasservices.DeployedCluster;
import unacloud.paas.back.iaasservices.DeployedClusterRol;
import unacloud.paas.back.puppet.PuppetMaster;
import unacloud.paas.back.sshutils.ProcessManager;
import unacloud.paas.back.user.FolderManager;
import unacloud.paas.back.utils.ThreadUtils;
import unacloud.paas.data.execution.FileDescriptionEntity;
import unacloud.paas.data.execution.RolDescription;
import unacloudws.responses.VirtualMachineExecutionWS;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static unacloud.paas.back.user.FolderManager.PLATFORMS_FOLDER;

public class ClusterManagerBean {
    public static void createExecution(long userId,long platformId,String executionName,String mainCommandArgs,RolDescription[] rolDescriptions,final List<FileDescriptionEntity> files){
        final User user=Ebean.find(User.class,userId);
        if(user==null)return;
        final Platform platform=Ebean.find(Platform.class,platformId);
        if(platform==null)return;
        final PlatformExecution platformExecution=new PlatformExecution();
        platformExecution.platform=platform;
        platformExecution.user=user;
        platformExecution.runName=executionName;
        platformExecution.status=ExecutionState.CREATING;
        platformExecution.eternal=false;
        platformExecution.startTime=new Date();
        platformExecution.mainCommandArgs=mainCommandArgs;
        platformExecution.rolExecution=new ArrayList<>();

        for(Rol rol:platform.roles){
            RolExecution rolExecution = new RolExecution();
            rolExecution.rol=rol;
            platformExecution.rolExecution.add(rolExecution);
            if(rol.id==platform.getMainCommand().getRoleId()){
                Command main=new Command();
                main.mainCommand=true;
                main.multiplicity=platform.getMainCommand().getMultiplicity();
                main.command=platform.getMainCommand().getCommand()+" "+mainCommandArgs;
                main.type=platform.getMainCommand().getResourceType();
                main.runningUser = platform.getMainCommand().runningUser;
                rolExecution.commands.add(main);
            }
        }
        for(RolDescription desc:rolDescriptions){
            System.out.println(" "+desc);
            for(RolExecution rolExecution: platformExecution.rolExecution ){
                if(desc.id==rolExecution.rol.id){
                    rolExecution.coresPerVM=desc.cores;
                    rolExecution.size=desc.size;
                    rolExecution.ramPerCore=1;
                    for(RolDescription.PuppetModule module:desc.modules){
                        System.out.println("  "+module );
                        PuppetModule pm=PuppetModule.find.byId(module.id);
                        if(pm!=null){
                            PuppetModuleUsage pmu=new PuppetModuleUsage();
                            pmu.puppetModule=pm;
                            rolExecution.puppetModuleUsage.add(pmu);
                        }
                    }
                }
            }
        }
        System.out.println(platformExecution);
        Ebean.save(platformExecution);
        System.out.println(platformExecution.getId());
        ThreadUtils.pool.submit(new Runnable() {
            @Override
            public void run() {
                startCluster(platformExecution, files,false);
            }
        });
    }
   public static void startCluster(PlatformExecution platformExecution, final List<FileDescriptionEntity> files,final boolean WaitParallel){
        FolderManager.createPlatformFolder(platformExecution.getId());

        try{
            LogManagerBean.storeLog(new ExecutionLog(platformExecution.getId(), null, "platform", "Starting platform..."));
            System.out.println("StartingPlatform");
            DeployedCluster deployedCluster=RuntimePlatformExecutionBean.startPlatform(platformExecution);

            System.out.println("StartedPlatform ");
            platformExecution.clusterExecutionId=deployedCluster.getId();
            platformExecution=insertNodesIntoRoles(platformExecution, deployedCluster);


            System.out.println("StartedPlatform:1");
            int tot=0;for(RolExecution rol:platformExecution.getRolExecution())tot+=rol.getNodes().size();
            LogManagerBean.storeLog(new ExecutionLog(platformExecution.getId(), null, "platform", "Configuring platform... "+tot));
            System.out.println("Configuring platform...");

            if(platformExecution.getPlatform().getName().equalsIgnoreCase("mpi")){
                try(PrintWriter pw=new PrintWriter(new File(PLATFORMS_FOLDER, "/"+Long.toHexString(platformExecution.getId())+"/machinesFile"))){
                    for(RolExecution re : platformExecution.getRolExecution()){
                        for(Node node : re.getNodes()){
                            pw.println(node.getHostname());
                        }
                    }
                }catch(FileNotFoundException ex){
                    ex.printStackTrace();
                }
            }
            if(files!=null){
                for(FileDescriptionEntity fde : files){
                    File dest=new File(new File(PLATFORMS_FOLDER, "/"+Long.toHexString(platformExecution.getId())+"/"), fde.getPath()+(fde.getPath().endsWith("/")?"":"/")+fde.getName());
                    try(FileOutputStream fos=new FileOutputStream(dest); InputStream is=fde.getContent()){
                        byte[] buffer=new byte[512];
                        for(int n; (n=is.read(buffer))!=-1;){
                            fos.write(buffer, 0, n);
                        }
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                    if(fde.isUnzip()){
                        new ProcessManager(null, "unzip "+dest.getAbsolutePath()+" -d "+dest.getParentFile().getAbsolutePath()).waitFor();
                        dest.delete();
                    }
                }
            }
            RuntimePlatformExecutionBean.configurePlatform(platformExecution);
            LogManagerBean.storeLog(new ExecutionLog(platformExecution.getId(), null, "platform", "Executing commands..."));
            RuntimePlatformExecutionBean.executeCommands(platformExecution);
            LogManagerBean.storeLog(new ExecutionLog(platformExecution.getId(), null, "platform", "Executing commands 2..."));
            PlatformExecutionManagerBean.updatePlatformExecutionState(platformExecution.getId(), ExecutionState.RUNNING);
            LogManagerBean.storeLog(new ExecutionLog(platformExecution.getId(), null, "platform", "Running..."));
        }catch(Throwable ex){
            ex.printStackTrace();
            stopCluster(platformExecution, ExecutionState.FAILED);
        }
   }
   private static PlatformExecution insertNodesIntoRoles(PlatformExecution platformExecution, DeployedCluster deployedCluster){
        for(DeployedClusterRol rolWs:deployedCluster.getRoles()){
            for(RolExecution rol:platformExecution.getRolExecution())if(rolWs.getId()==rol.getRol().getImageId()){
                rol.nodes=new ArrayList<Node>();
                for(VirtualMachineExecutionWS vmews:rolWs.getNodes()){
                    Node node=new Node();
                    node.hostname=vmews.getVirtualMachineName();
                    node.ipAddress=vmews.getVirtualMachineExecutionIP();
                    node.iaasExecutionId=""+vmews.getId();
                    rol.getNodes().add(node);
                }
                
            }
        }
        platformExecution.status=ExecutionState.STARTING;
       Ebean.save(platformExecution);
        return platformExecution;
    }
   public static void stopCluster(PlatformExecution platformExecution, ExecutionState state){
      PlatformExecutionManagerBean.updatePlatformExecutionState(platformExecution.id, state);
      LogManagerBean.storeLog(new ExecutionLog(platformExecution.id, null, "platform", "Stopping "+state+"..."));
      List<Node> nodes=new ArrayList<>();
      for(RolExecution rol:PlatformExecutionManagerBean.getPlatformExecution(platformExecution.id).getRolExecution()){
          nodes.addAll(rol.getNodes());
      }
      if(!nodes.isEmpty()){
         PuppetMaster.stopPuppetCluster(nodes);
         ClusterServices.stopCluster((int)platformExecution.clusterExecutionId);
      }
   }
}
