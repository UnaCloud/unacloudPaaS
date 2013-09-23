/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unacloud.paas.web.cloud;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import unacloud.paas.back.cluster.platforms.ClusterManager;
import unacloud.paas.data.entities.enums.ExecutionState;
import unacloud.paas.data.execution.PlatformExecutionEntity;
import unacloud.paas.data.managers.PlatformExecutionManager;
/**
 *
 * @author G
 */
@ManagedBean
@RequestScoped
public class PlatformExecutionListBean{
   List<PlatformExecutionEntity> runningList;
   public PlatformExecutionListBean(){
   }
   public List<PlatformExecutionEntity> getRunningList(){
      if(runningList==null)runningList=PlatformExecutionManager.getRunningPlatformExecutionList(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
      return runningList;
   }
   public void setRunningList(List<PlatformExecutionEntity> runningList){
      this.runningList=runningList;
   }
   public String stopPlatform(long id){
      //TODO update runningTable 
      ClusterManager.stopCluster(id, ExecutionState.CANCELED);
      return null;
   }
}