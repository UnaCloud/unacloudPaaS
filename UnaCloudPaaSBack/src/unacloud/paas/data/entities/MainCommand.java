package unacloud.paas.data.entities;
import java.io.Serializable;
import unacloud.paas.data.entities.enums.MultiplicityType;
import unacloud.paas.data.entities.enums.ResourceType;
/**
 *
 * @author G
 */
public class MainCommand implements Serializable{

   String command,roleId,user;
   ResourceType resourceType;
   MultiplicityType multiplicity;
   public MainCommand(){
   }
   public MainCommand(String command, String roleId, String user, ResourceType resourceType, MultiplicityType multiplicity){
      this.command=command;
      this.roleId=roleId;
      this.user=user;
      this.resourceType=resourceType;
      this.multiplicity=multiplicity;
   }
   public String getCommand(){
      return command;
   }
   public void setCommand(String command){
      this.command=command;
   }
   public String getRoleId(){
      return roleId;
   }
   public void setRoleId(String roleId){
      this.roleId=roleId;
   }
   public ResourceType getResourceType(){
      return resourceType;
   }
   public void setResourceType(ResourceType resourceType){
      this.resourceType=resourceType;
   }
   public MultiplicityType getMultiplicity(){
      return multiplicity;
   }
   public void setMultiplicity(MultiplicityType multiplicity){
      this.multiplicity=multiplicity;
   }
   public String getUser(){
      return user;
   }
   public void setUser(String user){
      this.user=user;
   }
   @Override
   public String toString(){
      return "MainCommand{"+"command="+command+", roleId="+roleId+", resourceType="+resourceType+", multiplicity="+multiplicity+'}';
   }
}