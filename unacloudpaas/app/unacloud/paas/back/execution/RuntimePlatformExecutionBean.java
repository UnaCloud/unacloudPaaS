package unacloud.paas.back.execution;

import models.Node;
import models.PlatformExecution;
import models.ExecutionLog;
import models.RolExecution;
import models.SSHSharedKey;
import unacloud.paas.back.iaasservices.ClusterServices;
import unacloud.paas.back.iaasservices.DeployedCluster;
import unacloud.paas.back.local.LocalHostTableManager;
import unacloud.paas.back.puppet.PuppetMaster;
import unacloud.paas.back.sshutils.SCP;
import unacloud.paas.back.utils.PaaSUtils;
import unacloud.paas.back.utils.SSHRSAPair;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class RuntimePlatformExecutionBean{
    
    public static DeployedCluster startPlatform(PlatformExecution plaformConfiguration){
        System.out.println("Starting cluster");
        DeployedCluster cluster=ClusterServices.startCluster(plaformConfiguration.generateClusterRequest());
        System.out.println("Cluster started");
        return cluster;
    }
   public static void configurePlatform(PlatformExecution plaformConfiguration){
      LocalHostTableManager.addPlatformHosts(plaformConfiguration);
      configureHostTable(plaformConfiguration);
      configureSSHKeys(plaformConfiguration);
      for(RolExecution rol:plaformConfiguration.getRolExecution()){
          RuntimeRoleExecutionBean.configureRole(plaformConfiguration, rol);
      }
      PuppetMaster.configurePuppetCluster(plaformConfiguration);
   }
   private static void configureSSHKeys(PlatformExecution plaformConfiguration){
      new File("./utils").mkdir();
      if(plaformConfiguration.getPlatform().getSshSharedKeys()==null||plaformConfiguration.getPlatform().getSshSharedKeys().isEmpty()){
         return;
      }
      SSHRSAPair keys=new SSHRSAPair();
      String[] masterId=PuppetMaster.getMasterRSAId();
      File authorized_keys=new File("./utils/authorizedkeys"+keys.getKeyId());
      for(RolExecution rol:plaformConfiguration.getRolExecution()){
          RuntimeRoleExecutionBean.sendFileToRole(plaformConfiguration,rol,keys.getRsaId(), "/root/.ssh/id_rsa");
      }
      List<SCP> copias=new LinkedList<>();
      for(RolExecution rol:plaformConfiguration.getRolExecution()){
         for(Node d : rol.getNodes()){
            String content=keys.getKeyType()+" "+keys.getPrivateKey()+" root@"+d.getHostname();
            File temp=new File("./utils/"+PaaSUtils.getMd5(content));
            try(PrintWriter pw=new PrintWriter(temp)){
               pw.println(content);
            }catch(Exception e2){
            }
            copias.add(new SCP(temp, d, "/root/.ssh/id_rsa.pub", new ExecutionLog(plaformConfiguration.getId(), null, "node:"+d.getHostname())));
         }
      }
      for(SCP copy : copias){
         copy.waitFor();
         copy.getSource().delete();
      }
      Map<String, RolExecution> roleMap=new TreeMap<>();
      for(RolExecution rol:plaformConfiguration.getRolExecution()){
         roleMap.put(rol.getRol().getName(), rol);
      }
      List<String> rolesToEnableSSHAccess=new ArrayList<>();
      for(RolExecution rol:plaformConfiguration.getRolExecution()){
         rolesToEnableSSHAccess.clear();
         for(SSHSharedKey relation : plaformConfiguration.getPlatform().getSshSharedKeys()){
            if(relation.getSourceRole()!=null&&relation.getTargetRole()!=null){
               RolExecution add=roleMap.get(relation.getSourceRole().getName());
               for(Node d : add.getNodes()){
                  rolesToEnableSSHAccess.add(d.getHostname());
               }
            }
         }
         if(!rolesToEnableSSHAccess.isEmpty()){
            try(PrintWriter pw=new PrintWriter(authorized_keys)){
               pw.println(masterId[0]+" "+masterId[1]+" root@puppetmaster");
               for(String d : rolesToEnableSSHAccess){
                  pw.println(keys.getKeyType()+" "+keys.getPrivateKey()+" root@"+d);
               }
            }catch(IOException e){
            }
            RuntimeRoleExecutionBean.sendFileToRole(plaformConfiguration, rol, authorized_keys, "/root/.ssh/authorized_keys");
         }
      }
      keys.clear();
      authorized_keys.delete();
   }
    
    public static void executeCommands(PlatformExecution plaformConfiguration){
        for(RolExecution rol:plaformConfiguration.getRolExecution()){
            RuntimeRoleExecutionBean.executeCommands(plaformConfiguration,rol);
        }
    }
    public static void configureHostTable(PlatformExecution platform) {
        File f = new File("./utils/hostable" + platform.getId());
        new File("./utils").mkdir();
        try (PrintWriter pw = new PrintWriter(f)) {
            pw.println("127.0.0.1\tlocalhost");
            pw.println("157.253.236.162\tpuppetmaster");
            for (RolExecution role : platform.getRolExecution()) {
                for (Node d : role.getNodes()) {
                    pw.println(d.getIpAddress() + "\t" + d.getHostname());
                }
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        for (RolExecution role : platform.getRolExecution()) {
            RuntimeRoleExecutionBean.sendFileToRole(platform,role,f, "/etc/hosts");
        }
        f.delete();
    }
}