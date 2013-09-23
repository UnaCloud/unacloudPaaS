/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unacloud.paas.web.admin;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import unacloud.paas.data.managers.PlatformExecutionManager;
/**
 *
 * @author G
 */
@ManagedBean
@RequestScoped
public class ContextMenuBean{
   int runningPlatforms=-1;
   /**
    * Creates a new instance of ContextMenuBean
    */
   public ContextMenuBean(){
   }
   public void setRunningPlatforms(int runningPlatforms){
      this.runningPlatforms=runningPlatforms;
   }
   public int getRunningPlatforms(){
      if(runningPlatforms==-1)runningPlatforms=PlatformExecutionManager.getRunningExecutionPlatformsCount(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
      return runningPlatforms;
   }
   
}
