/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unacloud.paas.data.execution;
import java.util.ArrayList;
import java.util.List;
import unacloud.paas.data.entities.PlatformRole;
import unacloud.paas.data.entities.PuppetModuleUsage;
/**
 *
 * @author G
 */
public class RoleExecutionEntity{
   int size,cores,coresPerNode;
   int RAMperCore=1024;
   PlatformRole roleConfig;
   List<NodeEntity> nodes=new ArrayList<>();
   List<CommandExecutionEntity> commandExecutions=new ArrayList<>();
   List<PuppetModuleUsage> puppetModules=new ArrayList<>();
   public RoleExecutionEntity(){
   }
   public RoleExecutionEntity(int size, int cores, int coresPerNode, PlatformRole roleConfig){
      this.size=size;
      this.cores=cores;
      this.coresPerNode=coresPerNode;
      this.roleConfig=roleConfig;
   }
   public int getSize(){
      return size;
   }
   public void setSize(int size){
      this.size=size;
   }
   public List<NodeEntity> getNodes(){
      return nodes;
   }
   public void setNodes(List<NodeEntity> nodes){
      this.nodes=nodes;
   }
   public int getCores(){
      return cores;
   }
   public void setCores(int cores){
      this.cores=cores;
   }
   public int getCoresPerNode(){
      return coresPerNode;
   }
   public void setCoresPerNode(int coresPerNode){
      this.coresPerNode=coresPerNode;
   }
   public PlatformRole getRoleConfig(){
      return roleConfig;
   }
   public void setRoleConfig(PlatformRole roleConfig){
      this.roleConfig=roleConfig;
   }
   public int getRAMperCore(){
      return RAMperCore;
   }
   public void setRAMperCore(int RAMperCore){
      this.RAMperCore=RAMperCore;
   }
   public List<CommandExecutionEntity> getCommandExecutions(){
      return commandExecutions;
   }
   public void setCommandExecutions(List<CommandExecutionEntity> commandExecutions){
      this.commandExecutions=commandExecutions;
   }
   public List<PuppetModuleUsage> getPuppetModules(){
      return puppetModules;
   }
   public void setPuppetModules(List<PuppetModuleUsage> puppetModules){
      this.puppetModules=puppetModules;
   }
}