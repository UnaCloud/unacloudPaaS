/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unacloud.paas.web.cloud;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.ejb.EJB;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import unacloud.paas.back.beans.ClusterManagerBean;
import unacloud.paas.back.beans.PlatformExecutionManagerBean;
import unacloud.paas.back.database.entities.PlatformExecution;
import unacloud.paas.back.database.enums.ExecutionState;
/**
 *
 * @author G
 */
@ManagedBean
@RequestScoped
public class PlatformExecutionListBean{
    List<PlatformExecution> runningList;
    @EJB
    PlatformExecutionManagerBean platformExecutionManagerBean;
    @EJB
    ClusterManagerBean clusterManagerBean;
   public PlatformExecutionListBean(){
   }
   public List<PlatformExecution> getRunningList(){
      if(runningList==null)runningList=platformExecutionManagerBean.getRunningPlatformExecutionList(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
      return runningList;
   }
   public void setRunningList(List<PlatformExecution> runningList){
      this.runningList=runningList;
   }
   public String stopPlatform(long id){
      //TODO update runningTable 
      clusterManagerBean.stopCluster(id, ExecutionState.CANCELED);
      return null;
   }
}