/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unacloud.paas.back.beans;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import unacloud.paas.back.Main;
import unacloud.paas.back.database.entities.ExecutionLog;
import unacloud.paas.back.database.entities.Node;
import unacloud.paas.back.database.entities.PlatformExecution;
import unacloud.paas.back.database.entities.RolExecution;
import unacloud.paas.back.database.enums.ExecutionState;
import unacloud.paas.back.execution.RuntimePlatformExecutionBean;
import unacloud.paas.back.execution.RuntimeRoleExecutionBean;
import unacloud.paas.back.iaasservices.ClusterServices;
import unacloud.paas.back.iaasservices.DeployedCluster;
import unacloud.paas.back.iaasservices.DeployedClusterRol;
import unacloud.paas.back.puppet.PuppetMaster;
import unacloud.paas.back.sshutils.ProcessManager;
import unacloud.paas.back.user.FolderManager;
import static unacloud.paas.back.user.FolderManager.PLATFORMS_FOLDER;
import unacloud.paas.data.execution.FileDescriptionEntity;
import unacloud.paas.failrecovery.PlatformSucessManager;
import unacloudws.responses.VirtualMachineExecutionWS;

/**
 *
 * @author G
 */
@Stateless
@LocalBean
public class ClusterManagerBean {
    @EJB
    private LogManagerBean logManagerBean;
    @EJB
    private RuntimePlatformExecutionBean runtimePlatformExecutionBean;
    @EJB
    private PlatformExecutionManagerBean platformExecutionManager;
    
    @PersistenceContext()
    private EntityManager entityManager;

    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    public void createCluster(final PlatformExecution platformExecution, final String username, final List<FileDescriptionEntity> files,boolean waitParallel){
      if(platformExecution==null){
         return;
      }
      if(!platformExecutionManager.createPlatformExecution(platformExecution, username)){
         return;
      }
      Main.start();
      startCluster(platformExecution, files,waitParallel);
   }
    @Asynchronous
   public void startCluster(PlatformExecution platformExecution, final List<FileDescriptionEntity> files,final boolean WaitParallel){
        FolderManager.createPlatformFolder(platformExecution.getId());

        logManagerBean.storeLog(new ExecutionLog(platformExecution.getId(), "platform", "Starting platform..."));
        Logger.getLogger("PaaS").log(Level.INFO,"StartingPlatform");
        DeployedCluster deployedCluster=runtimePlatformExecutionBean.startPlatform(platformExecution);
        
        Logger.getLogger("PaaS").log(Level.INFO,"StartedPlatform ");
        platformExecution=insertNodesIntoRoles(platformExecution, deployedCluster);
        
        Logger.getLogger("PaaS").log(Level.INFO,"StartedPlatform:1");
        int tot=0;for(RolExecution rol:platformExecution.getRolExecution())tot+=rol.getNodes().size();
        logManagerBean.storeLog(new ExecutionLog(platformExecution.getId(), "platform", "Configuring platform... "+tot));
        Logger.getLogger("PaaS").log(Level.INFO,"Configuring platform...");
        
        try{
           if(platformExecution.getPlatform().getId()==1){
              try(PrintWriter pw=new PrintWriter(new File(PLATFORMS_FOLDER, "/"+Long.toHexString(platformExecution.getId())+"/machinesFile"))){
                 for(RolExecution re : platformExecution.getRolExecution()){
                    for(Node node : re.getNodes()){
                       pw.println(node.getHostname());
                    }
                 }
              }catch(FileNotFoundException ex){
                 Logger.getLogger(ClusterManagerBean.class.getName()).log(Level.SEVERE, null, ex);
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
                    Logger.getLogger(ClusterManagerBean.class.getName()).log(Level.SEVERE, null, ex);
                 }
                 if(fde.isUnzip()){
                    new ProcessManager(null, "unzip "+dest.getAbsolutePath()+" -d "+dest.getParentFile().getAbsolutePath()).waitFor();
                    dest.delete();
                 }
              }
           }
           runtimePlatformExecutionBean.configurePlatform(platformExecution);
           logManagerBean.storeLog(new ExecutionLog(platformExecution.getId(), "platform", "Executing commands..."));
           runtimePlatformExecutionBean.executeCommands(platformExecution);
           logManagerBean.storeLog(new ExecutionLog(platformExecution.getId(), "platform", "Executing commands 2..."));
           platformExecutionManager.updatePlatformExecutionState(platformExecution.getId(), ExecutionState.RUNNING);
           logManagerBean.storeLog(new ExecutionLog(platformExecution.getId(), "platform", "Running..."));
        }catch(Throwable ex){
           stopCluster(platformExecution.getId(), ExecutionState.FAILED);
        }
   }
   private PlatformExecution insertNodesIntoRoles(PlatformExecution platformExecution, DeployedCluster deployedCluster){
        for(DeployedClusterRol rolWs:deployedCluster.getRoles()){
            for(RolExecution rol:platformExecution.getRolExecution())if(rolWs.getId()==rol.getRol().getImageId()){
                rol.setNodes(new ArrayList<Node>());
                for(VirtualMachineExecutionWS vmews:rolWs.getNodes()){
                    Node node=new Node();
                    node.setHostname(vmews.getVirtualMachineName());
                    node.setIpAddress(vmews.getVirtualMachineExecutionIP());
                    node.setIaasExecutionId(""+vmews.getId());
                    rol.getNodes().add(node);
                }
                
            }
        }
        platformExecution.setStatus(ExecutionState.STARTING);
        return entityManager.merge(platformExecution);
    }
   public void stopCluster(long platformExecutionId, ExecutionState state){
      platformExecutionManager.updatePlatformExecutionState(platformExecutionId, state);
      logManagerBean.storeLog(new ExecutionLog(platformExecutionId, "platform", "Stopping "+state+"..."));
      List<Node> nodes=new ArrayList<>();
      for(RolExecution rol:platformExecutionManager.getPlatformExecution(platformExecutionId).getRolExecution()){
          nodes.addAll(rol.getNodes());
      }
      if(!nodes.isEmpty()){
         PuppetMaster.stopPuppetCluster(nodes);
         ClusterServices.stopCluster(1);
      }
   }
   public static ClusterManagerBean getStaticInstance() {
        try {
            Context c = new InitialContext();
            return (ClusterManagerBean) c.lookup("java:global/UnaCloudPaaSBack/ClusterManagerBean!unacloud.paas.back.beans.ClusterManagerBean");
        } catch (NamingException ne) {
            Logger.getLogger(PlatformSucessManager.class.getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
