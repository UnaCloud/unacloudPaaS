/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unacloud.paas.web.cloud;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import unacloud.paas.back.database.entities.UserFile;
import unacloud.paas.back.user.FolderManager;
import unacloud.paas.back.user.UserFileEntity;
/**
 *
 * @author G
 */
@ManagedBean
@RequestScoped
public class FileSystemBean{
   List<UserFile> list;
   
   @EJB
   FolderManager folderManager;
   
   public FileSystemBean(){
   }
   public List<UserFile> getList(){
      if(list==null)list=folderManager.getUserFiles(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
      return list;
   }
   public void setList(List<UserFile> list){
      this.list=list;
   }
   public String deleteFile(long id){
      folderManager.deleteUserFile(id,FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
      return "userfiles";
   }
   
}
