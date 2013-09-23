/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unacloud.paas.web.cloud;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import unacloud.paas.back.cluster.platforms.ClusterManager;
import unacloud.paas.back.user.FolderManager;
import unacloud.paas.data.entities.MainCommand;
import unacloud.paas.data.entities.PlatformRole;
import unacloud.paas.data.entities.PuppetModule;
import unacloud.paas.data.entities.PuppetModuleUsage;
import unacloud.paas.data.entities.enums.ResourceType;
import unacloud.paas.data.execution.CommandExecutionEntity;
import unacloud.paas.data.execution.FileDescriptionEntity;
import unacloud.paas.data.execution.PlatformExecutionEntity;
import unacloud.paas.data.execution.RoleExecutionEntity;
import unacloud.paas.data.managers.PlatformExecutionManager;
import unacloud.paas.data.managers.PuppetModuleManager;
/**
 *
 * @author G
 */
@ManagedBean
@ViewScoped
public class NewRunBean implements Serializable{
   PlatformExecutionEntity toRun=null;
   String mainCommandArgs;
   List<FileDescriptionEntity> files=new ArrayList<>();
   PuppetModule puppetModule;
   String roleToAddNewPuppetModule;
   public NewRunBean(){
   }
   public String selectPlatform(int platformId){
      toRun=PlatformExecutionManager.generateVoidPlatformExecution(platformId);
      return null;
   }
   public PlatformExecutionEntity getToRun(){
      return toRun;
   }
   public void setToRun(PlatformExecutionEntity toRun){
      this.toRun=toRun;
   }
   
   public List<FileDescriptionEntity> getFiles(){
      return files;
   }
   public void setFiles(List<FileDescriptionEntity> files){
      this.files=files;
   }
   public String startPlatform(){
      MainCommand mainCommand=toRun.getPlatform().getMainCommand();
      if(mainCommand!=null&&mainCommandArgs!=null){
         for(RoleExecutionEntity role:toRun.getRoles())if(role.getRoleConfig().getRoleName().equals(mainCommand.getRoleId())){
            for(CommandExecutionEntity command:role.getCommandExecutions())if(command.isMainCommand()){
               command.setCommand(mainCommand.getCommand().trim()+" "+mainCommandArgs.trim());
            }
         }
      }
      ClusterManager.createCluster(toRun, FacesContext.getCurrentInstance().getExternalContext().getRemoteUser(), files);
      return "running";
   }
   public PuppetModule getPuppetModule(){
      return puppetModule;
   }
   public void setPuppetModule(PuppetModule puppetModule){
      this.puppetModule=puppetModule;
   }
   public List<PuppetModule> completeModule(String query){
      if(query==null||query.isEmpty()){
         return Collections.EMPTY_LIST;
      }
      return PuppetModuleManager.getPuppetModules(query);
   }
   public String getRoleToAddNewPuppetModule(){
      return roleToAddNewPuppetModule;
   }
   public void setRoleToAddNewPuppetModule(String roleToAddNewPuppetModule){
      this.roleToAddNewPuppetModule=roleToAddNewPuppetModule;
   }
   public String clear(){
      puppetModule=null;
      return null;
   }
   public String addNewModule(String roleName){
      roleToAddNewPuppetModule=roleName;
      puppetModule=null;
      return null;
   }
   public String addModule(){
      int e=0;
      if(toRun!=null)for(RoleExecutionEntity r : toRun.getRoles()){
         if(r.getRoleConfig().getRoleName().equals(roleToAddNewPuppetModule)){
            r.getPuppetModules().add(new PuppetModuleUsage(-1, puppetModule));
            RequestContext.getCurrentInstance().execute("addModuleDialogWv.hide();");
            RequestContext.getCurrentInstance().update("mainForm:rolesTable:"+e+":rol");
            puppetModule=null;
         }
         e++;
      }
      return null;
   }
   public String addLocalFile(String path,String name){
      try{
         files.add(new FileDescriptionEntity(name,path,new FileInputStream(FolderManager.getFile(name, path,FacesContext.getCurrentInstance().getExternalContext().getRemoteUser())),ResourceType.GLOBAL, toRun.getPlatform().getRoles().get(0).getRoleName()));
      }catch(FileNotFoundException ex){
         Logger.getLogger(NewRunBean.class.getName()).log(Level.SEVERE, null, ex);
      }
      return null;
   }
   public void handleFileUpload(FileUploadEvent event){
      try{
         files.add(new FileDescriptionEntity(event.getFile().getFileName(), "/", event.getFile().getInputstream(), ResourceType.GLOBAL, toRun.getPlatform().getRoles().get(0).getRoleName()));
      }catch(IOException ex){
         Logger.getLogger(NewRunBean.class.getName()).log(Level.SEVERE, null, ex);
      }
   }
   public String getMainCommandArgs(){
      return mainCommandArgs;
   }
   public void setMainCommandArgs(String mainCommandArgs){
      this.mainCommandArgs=mainCommandArgs;
   }
}