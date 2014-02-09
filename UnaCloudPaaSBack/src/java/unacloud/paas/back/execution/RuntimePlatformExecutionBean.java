/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unacloud.paas.back.execution;
import unacloud.paas.back.execution.entities.RuntimeExecutionLog;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import unacloud.paas.back.database.entities.Node;
import unacloud.paas.back.database.entities.PlatformExecution;
import unacloud.paas.back.database.entities.RolExecution;
import unacloud.paas.back.database.entities.SSHSharedKey;
import unacloud.paas.back.puppet.PuppetMaster;
import unacloud.paas.back.iaasservices.ClusterServices;
import unacloud.paas.back.iaasservices.DeployedCluster;
import unacloud.paas.back.local.LocalHostTableManager;
import unacloud.paas.back.sshutils.SCP;
import unacloud.paas.back.utils.PaaSUtils;
import unacloud.paas.back.utils.SSHRSAPair;

@Stateless
@LocalBean
public class RuntimePlatformExecutionBean{
    
    @EJB
    private RuntimeRoleExecutionBean runtimeRoleExecutionBean;
    public DeployedCluster startPlatform(PlatformExecution plaformConfiguration){
        Logger.getLogger("PaaS").log(Level.INFO, "Starting cluster");
        DeployedCluster cluster=ClusterServices.startCluster(plaformConfiguration.generateClusterRequest());
        Logger.getLogger("PaaS").log(Level.INFO, "Cluster started");
        return cluster;
    }
   public void configurePlatform(PlatformExecution plaformConfiguration){
      LocalHostTableManager.addPlatformHosts(plaformConfiguration);
      configureHostTable(plaformConfiguration);
      configureSSHKeys(plaformConfiguration);
      for(RolExecution rol:plaformConfiguration.getRolExecution()){
          runtimeRoleExecutionBean.configureRole(plaformConfiguration, rol);
      }
      PuppetMaster.configurePuppetCluster(plaformConfiguration);
   }
   private void configureSSHKeys(PlatformExecution plaformConfiguration){
      new File("./utils").mkdir();
      if(plaformConfiguration.getPlatform().getSshSharedKeys()==null||plaformConfiguration.getPlatform().getSshSharedKeys().isEmpty()){
         return;
      }
      SSHRSAPair keys=new SSHRSAPair();
      String[] masterId=PuppetMaster.getMasterRSAId();
      File authorized_keys=new File("./utils/authorizedkeys"+keys.getKeyId());
      for(RolExecution rol:plaformConfiguration.getRolExecution()){
          runtimeRoleExecutionBean.sendFileToRole(plaformConfiguration,rol,keys.getRsaId(), "/root/.ssh/id_rsa");
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
            copias.add(new SCP(temp, d, "/root/.ssh/id_rsa.pub", new RuntimeExecutionLog(plaformConfiguration.getId(), "node:"+d.getHostname())));
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
            runtimeRoleExecutionBean.sendFileToRole(plaformConfiguration, rol, authorized_keys, "/root/.ssh/authorized_keys");
         }
      }
      keys.clear();
      authorized_keys.delete();
   }
    
    public void executeCommands(PlatformExecution plaformConfiguration){
        for(RolExecution rol:plaformConfiguration.getRolExecution()){
            runtimeRoleExecutionBean.executeCommands(plaformConfiguration,rol);
        }
    }
    public void configureHostTable(PlatformExecution platform) {
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
            Logger.getLogger(ClusterServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (RolExecution role : platform.getRolExecution()) {
            runtimeRoleExecutionBean.sendFileToRole(platform,role,f, "/etc/hosts");
        }
        f.delete();
    }
}