/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unacloud.paas.web.cloud;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import unacloud.paas.back.Main;
import unacloud.paas.data.execution.PlatformExecutionEntity;
import unacloud.paas.data.managers.PlatformExecutionManager;
/**
 *
 * @author G
 */
@ManagedBean
@ViewScoped
public class RunningHistoryBean{
   List<PlatformExecutionEntity> list;
   long platformToDelete;
   public RunningHistoryBean(){
      Main.start();
   }
   public List<PlatformExecutionEntity> getList(){
      if(list==null){
         list=PlatformExecutionManager.getPlatformExecutionList(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
      }
      return list;
   }
   public void setList(List<PlatformExecutionEntity> list){
      this.list=list;
   }
   public String toDeletePlatform(long id){
      platformToDelete=id;
      return null;
   }
   public String deletePlatform(){
      PlatformExecutionManager.deletePlatformExecution(platformToDelete);
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
