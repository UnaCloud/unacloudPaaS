/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unacloud.paas.web.cloud;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import unacloud.paas.back.beans.PlatformExecutionManagerBean;
import unacloud.paas.back.database.entities.ExecutionLog;
import unacloud.paas.back.database.entities.PlatformExecution;
/**
 *
 * @author G
 */
@ManagedBean
@ViewScoped
public class PlatformExecutionBean implements Serializable{
    PlatformExecution selectedPlatform=null;
    List<ExecutionLog> log=null;
    long selectedId;
    @EJB
    PlatformExecutionManagerBean platformExecutionManagerBean;
   public PlatformExecutionBean(){
   }
   public PlatformExecution getSelectedPlatform(){
      if(selectedPlatform==null)selectedPlatform=platformExecutionManagerBean.getPlatformExecution(selectedId);
      return selectedPlatform;
   }
   public void setSelectedPlatform(PlatformExecution selectedPlatform){
      this.selectedPlatform=selectedPlatform;
   }
   public long getSelectedId(){
      return selectedId;
   }
   public void setSelectedId(long selectedId){
      this.selectedId=selectedId;
   }
   public List<ExecutionLog> getLog(){
      if(log==null){
          log=platformExecutionManagerBean.getPlatformLog(selectedId);
      }
      return log;
   }
   public void setLog(List<ExecutionLog> log){
      this.log=log;
   }
}