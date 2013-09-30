/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unacloud.paas.back.execution;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import unacloud.paas.back.puppet.PuppetMaster;
import unacloud.paas.back.iaasservices.ClusterServices;
import unacloud.paas.back.local.LocalHostTableManager;
import unacloud.paas.back.sshutils.SCP;
import unacloud.paas.back.utils.PaaSUtils;
import unacloud.paas.back.utils.SSHRSAPair;
import unacloud.paas.data.execution.PlatformExecutionEntity;
import unacloud.paas.data.execution.RoleExecutionEntity;
/**
 *
 * @author G
 */
public class PlatformExecution{
   long platformExecutionId;
   PlatformExecutionEntity plaformConfiguration;
   List<RoleExecution> roles=new ArrayList<>();
   public PlatformExecution(long platformExecutionId, PlatformExecutionEntity plaformConfiguration){
      this.platformExecutionId=platformExecutionId;
      this.plaformConfiguration=plaformConfiguration;
      for(RoleExecutionEntity role : plaformConfiguration.getRoles()){
         roles.add(new RoleExecution(role, platformExecutionId, this));
      }
   }
   public void configurePlatform(){
      LocalHostTableManager.addPlatformHosts(this);
      ClusterServices.configureHostTable(this);
      configureSSHKeys();
      for(RoleExecution role : roles){
         role.configureRole();
      }
      PuppetMaster.configurePuppetCluster(this);
   }
   private void configureSSHKeys(){
      new File("./utils").mkdir();
      if(plaformConfiguration.getPlatform().getSshSharedKeys()==null||plaformConfiguration.getPlatform().getSshSharedKeys().isEmpty()){
         return;
      }
      SSHRSAPair keys=new SSHRSAPair();
      String[] masterId=PuppetMaster.getMasterRSAId();
      File authorized_keys=new File("./utils/authorizedkeys"+keys.getKeyId());
      for(RoleExecution role : roles){
         role.sendFileToRole(keys.getRsaId(), "/root/.ssh/id_rsa");
      }
      List<SCP> copias=new LinkedList<>();
      for(RoleExecution role : roles){
         for(NodeExecution d : role.nodes){
            String content=keys.getKeyType()+" "+keys.getPrivateKey()+" root@"+d.getHostname();
            File temp=new File("./utils/"+PaaSUtils.getMd5(content));
            try(PrintWriter pw=new PrintWriter(temp)){
               pw.println(content);
            }catch(Exception e2){
            }
            copias.add(new SCP(temp, d, "/root/.ssh/id_rsa.pub", new ExecutionLog(platformExecutionId, "node:"+d.getHostname())));
         }
      }
      for(SCP copy : copias){
         copy.waitFor();
         copy.getSource().delete();
      }
      Map<String, RoleExecution> roleMap=new TreeMap<>();
      for(RoleExecution role : roles){
         roleMap.put(role.getRoleName(), role);
      }
      List<String> rolesToEnableSSHAccess=new ArrayList<>();
      for(RoleExecution role : roles){
         rolesToEnableSSHAccess.clear();
         for(String[] relation : plaformConfiguration.getPlatform().getSshSharedKeys()){
            if(relation.length==2&&relation[1].equals(role.getRoleName())){
               RoleExecution add=roleMap.get(relation[0]);
               for(NodeExecution d : add.getNodes()){
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
            role.sendFileToRole(authorized_keys, "/root/.ssh/authorized_keys");
         }
      }
      keys.clear();
      authorized_keys.delete();
   }
   public void startPlatform(){
      for(RoleExecution role : roles){
         role.startRole();
      }
   }
   public void executeCommands(){
      for(RoleExecution role : roles){
         role.executeCommands();
      }
   }
   public List<RoleExecution> getRoles(){
      return roles;
   }
   public long getPlatformExecutionId(){
      return platformExecutionId;
   }
}