/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unacloud.paas.web.cloud;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import unacloud.paas.back.user.FolderManager;
/**
 *
 * @author G
 */
@ManagedBean
@ViewScoped
public class FileSystemAddBean{
   String name;
   String path;
   UploadedFile file; 
   /**
    * Creates a new instance of FileSystemAddBean
    */
   public FileSystemAddBean(){
   }
   public void handleFileUpload(FileUploadEvent event){
      name=event.getFile().getFileName();
      path="/";
      file=event.getFile();
      RequestContext.getCurrentInstance().execute("addFileDialogWv.show();");
      /*try{
         //files.add(new FileDescriptionEntity(event.getFile().getFileName(), "/"+event.getFile().getFileName(), event.getFile().getInputstream(), ResourceType.GLOBAL, selectedPlatform.getRoles().get(0).getRoleName()));
      }catch(IOException ex){
         Logger.getLogger(NewRunBean.class.getName()).log(Level.SEVERE, null, ex);
      }*/
   }
   public String getName(){
      return name;
   }
   public void setName(String name){
      this.name=name;
   }
   public String getPath(){
      return path;
   }
   public void setPath(String path){
      this.path=path;
   }
   public UploadedFile getFile(){
      return file;
   }
   public void setFile(UploadedFile file){
      this.file=file;
   }
   public String addFile(){
      try{
         FolderManager.uploadUserFile(path,name, FacesContext.getCurrentInstance().getExternalContext().getRemoteUser(),file.getInputstream());
         path=name=null;
         file=null;
      }catch(IOException ex){
         Logger.getLogger(FileSystemAddBean.class.getName()).log(Level.SEVERE, null, ex);
      }
      return null;
   }
}
