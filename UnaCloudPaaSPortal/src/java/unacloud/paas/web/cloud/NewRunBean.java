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
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import unacloud.paas.back.beans.ClusterManagerBean;
import unacloud.paas.back.beans.PlatformExecutionManagerBean;
import unacloud.paas.back.beans.PuppetModuleManagerBean;
import unacloud.paas.back.database.entities.Command;
import unacloud.paas.back.database.entities.MainCommand;
import unacloud.paas.back.database.entities.PlatformExecution;
import unacloud.paas.back.database.entities.PuppetModule;
import unacloud.paas.back.database.entities.PuppetModuleUsage;
import unacloud.paas.back.database.entities.RolExecution;
import unacloud.paas.back.database.enums.ResourceType;
import unacloud.paas.back.user.FolderManager;
import unacloud.paas.data.execution.FileDescriptionEntity;
/**
 *
 * @author G
 */
@ManagedBean
@ViewScoped
public class NewRunBean implements Serializable{
    PlatformExecution toRun=null;
    String mainCommandArgs;
    List<FileDescriptionEntity> files=new ArrayList<>();
    PuppetModule puppetModule;
    String roleToAddNewPuppetModule;
    
    @EJB
    PlatformExecutionManagerBean platformExecutionManagerBean;
    @EJB
    ClusterManagerBean clusterManagerBean;
    @EJB
    PuppetModuleManagerBean puppetModuleManagerBean;
   public NewRunBean(){
   }
   public String selectPlatform(int platformId){
      toRun=platformExecutionManagerBean.generateVoidPlatformExecution(platformId);
      return null;
   }
   public PlatformExecution getToRun(){
      return toRun;
   }
   public void setToRun(PlatformExecution toRun){
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
         for(RolExecution role:toRun.getRolExecution())if(role.getRol().getId()==mainCommand.getRoleId()){
            for(Command command:role.getCommand())if(command.getMainCommand()){
               command.setCommand(mainCommand.getCommand().trim()+" "+mainCommandArgs.trim());
            }
         }
      }
      clusterManagerBean.createCluster(toRun, FacesContext.getCurrentInstance().getExternalContext().getRemoteUser(), files,false);
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
      return puppetModuleManagerBean.getPuppetModules(query);
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
      if(toRun!=null)for(RolExecution r : toRun.getRolExecution()){
         if(r.getRol().getName().equals(roleToAddNewPuppetModule)){
            r.getPuppetModuleUsage().add(new PuppetModuleUsage(puppetModule));
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
         files.add(new FileDescriptionEntity(name,path,new FileInputStream(FolderManager.getFile(name, path,FacesContext.getCurrentInstance().getExternalContext().getRemoteUser())),ResourceType.GLOBAL, toRun.getPlatform().getRol().get(0).getName()));
      }catch(FileNotFoundException ex){
         Logger.getLogger(NewRunBean.class.getName()).log(Level.SEVERE, null, ex);
      }
      return null;
   }
   public void handleFileUpload(FileUploadEvent event){
      try{
         files.add(new FileDescriptionEntity(event.getFile().getFileName(), "/", event.getFile().getInputstream(), ResourceType.GLOBAL, toRun.getPlatform().getRol().get(0).getName()));
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