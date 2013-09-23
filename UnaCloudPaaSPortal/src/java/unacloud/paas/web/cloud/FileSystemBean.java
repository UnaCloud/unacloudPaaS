/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unacloud.paas.web.cloud;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import unacloud.paas.back.user.FolderManager;
import unacloud.paas.back.user.UserFileEntity;
/**
 *
 * @author G
 */
@ManagedBean
@RequestScoped
public class FileSystemBean{
   List<UserFileEntity> list;
   /**
    * Creates a new instance of FileSystemBean
    */
   public FileSystemBean(){
   }
   public List<UserFileEntity> getList(){
      if(list==null)list=FolderManager.getUserFiles(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
      return list;
   }
   public void setList(List<UserFileEntity> list){
      this.list=list;
   }
   public String deleteFile(String name,String path){
      FolderManager.deleteUserFile(path, name,FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
      return "userfiles";
   }
   
}
