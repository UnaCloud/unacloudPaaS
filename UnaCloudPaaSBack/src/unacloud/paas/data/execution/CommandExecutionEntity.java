/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unacloud.paas.data.execution;
import java.io.Serializable;
import unacloud.paas.data.entities.enums.MultiplicityType;
import unacloud.paas.data.entities.enums.ResourceType;
/**
 *
 * @author G
 */
public class CommandExecutionEntity implements Serializable{
   long id;
   String roleName;
   String command;
   String user;
   MultiplicityType multiplicity;
   ResourceType resourceType;
   boolean mainCommand;
   public CommandExecutionEntity(){
   }
   public CommandExecutionEntity(String roleName, String command, MultiplicityType multiplicity, ResourceType resourceType){
      this.roleName=roleName;
      this.command=command;
      this.multiplicity=multiplicity;
      this.resourceType=resourceType;
   }
   public long getId(){
      return id;
   }
   public void setId(long id){
      this.id=id;
   }
   public String getRoleName(){
      return roleName;
   }
   public void setRoleName(String roleName){
      this.roleName=roleName;
   }
   public String getCommand(){
      return command;
   }
   public void setCommand(String command){
      this.command=command;
   }
   public MultiplicityType getMultiplicity(){
      return multiplicity;
   }
   public void setMultiplicity(MultiplicityType multiplicity){
      this.multiplicity=multiplicity;
   }
   public ResourceType getResourceType(){
      return resourceType;
   }
   public void setResourceType(ResourceType resourceType){
      this.resourceType=resourceType;
   }
   @Override
   public String toString(){
      return "CommandExecutionEntity{"+"id="+id+", roleName="+roleName+", command="+command+", user="+user+", multiplicity="+multiplicity+", resourceType="+resourceType+", mainCommand="+mainCommand+'}';
   }
   public boolean isMainCommand(){
      return mainCommand;
   }
   public void setMainCommand(boolean mainCommand){
      this.mainCommand=mainCommand;
   }
   public String getUser(){
      return user;
   }
   public void setUser(String user){
      this.user=user;
   }
}
