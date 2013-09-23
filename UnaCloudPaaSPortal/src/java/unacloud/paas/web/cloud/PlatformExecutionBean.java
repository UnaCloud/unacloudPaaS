/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unacloud.paas.web.cloud;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import unacloud.paas.data.entities.LogEntity;
import unacloud.paas.data.execution.PlatformExecutionEntity;
import unacloud.paas.data.managers.PlatformExecutionManager;
/**
 *
 * @author G
 */
@ManagedBean
@ViewScoped
public class PlatformExecutionBean implements Serializable{
   PlatformExecutionEntity selectedPlatform=null;
   List<LogEntity> log=null;
   long selectedId;
   public PlatformExecutionBean(){
   }
   public PlatformExecutionEntity getSelectedPlatform(){
      if(selectedPlatform==null)selectedPlatform=PlatformExecutionManager.getPlatformExecution(selectedId);
      return selectedPlatform;
   }
   public void setSelectedPlatform(PlatformExecutionEntity selectedPlatform){
      this.selectedPlatform=selectedPlatform;
   }
   public long getSelectedId(){
      return selectedId;
   }
   public void setSelectedId(long selectedId){
      this.selectedId=selectedId;
   }
   public List<LogEntity> getLog(){
      if(log==null){
         log=PlatformExecutionManager.getPlatformLog(selectedId);
      }
      return log;
   }
   public void setLog(List<LogEntity> log){
      this.log=log;
   }
}