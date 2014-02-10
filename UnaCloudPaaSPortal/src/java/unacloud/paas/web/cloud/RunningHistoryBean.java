/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unacloud.paas.web.cloud;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import org.primefaces.context.RequestContext;
import unacloud.paas.back.Main;
import unacloud.paas.back.beans.PlatformExecutionManagerBean;
import unacloud.paas.back.database.entities.PlatformExecution;
/**
 *
 * @author G
 */
@ManagedBean
@ViewScoped
public class RunningHistoryBean{
    List<PlatformExecution> list;
    long platformToDelete;
    @EJB
    PlatformExecutionManagerBean platformExecutionManagerBean;
   public RunningHistoryBean(){
      Main.start();
   }
   public List<PlatformExecution> getList(){
      if(list==null){
         list=platformExecutionManagerBean.getPlatformExecutionList(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
      }
      return list;
   }
   public void setList(List<PlatformExecution> list){
      this.list=list;
   }
   public String toDeletePlatform(long id){
      platformToDelete=id;
      return null;
   }
   public String deletePlatform(){
      platformExecutionManagerBean.deletePlatformExecution(platformToDelete);
      platformToDelete=0;
      RequestContext.getCurrentInstance().execute("confirmDelete.hide();");
      return "runningHistory";
   }
   public long getPlatformToDelete(){
      return platformToDelete;
   }
   public void setPlatformToDelete(long platformToDelete){
      this.platformToDelete=platformToDelete;
   }
}
