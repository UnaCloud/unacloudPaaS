package unacloud.paas.data.execution;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import unacloud.paas.data.entities.Platform;
import unacloud.paas.data.entities.enums.ExecutionState;
public class PlatformExecutionEntity implements Serializable{
   long id;
   String runName;
   Platform platform;
   int state;
   String stateName;
   List<RoleExecutionEntity> roles=new ArrayList<>();
   public PlatformExecutionEntity(){
   }
   public PlatformExecutionEntity(long id, String runName, Platform platform){
      this.id=id;
      this.runName=runName;
      this.platform=platform;
   }
   public PlatformExecutionEntity(long id, String runName, Platform platform, int state, String stateName){
      this.id=id;
      this.runName=runName;
      this.platform=platform;
      this.state=state;
      this.stateName=stateName;
   }
   public long getId(){
      return id;
   }
   public String getHexId(){
      return Long.toHexString(id);
   }
   public void setId(long id){
      this.id=id;
   }
   public String getRunName(){
      return runName;
   }
   public void setRunName(String runName){
      this.runName=runName;
   }
   public Platform getPlatform(){
      return platform;
   }
   public void setPlatform(Platform platform){
      this.platform=platform;
   }
   public int getState(){
      return state;
   }
   public void setState(int state){
      this.state=state;
   }
   public String getStateName(){
      return ExecutionState.getFromId(getState()).toString();
   }
   public void setStateName(String stateName){
      this.stateName=stateName;
   }
   public List<RoleExecutionEntity> getRoles(){
      return roles;
   }
   public void setRoles(List<RoleExecutionEntity> roles){
      this.roles=roles;
   }
}
