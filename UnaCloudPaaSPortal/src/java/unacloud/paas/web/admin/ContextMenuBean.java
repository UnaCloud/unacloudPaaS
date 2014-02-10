/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unacloud.paas.web.admin;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import unacloud.paas.back.beans.PlatformExecutionManagerBean;

/**
 *
 * @author G
 */
@ManagedBean
@RequestScoped
public class ContextMenuBean {

    @EJB
    PlatformExecutionManagerBean platformExecutionManagerBean;
    
    int runningPlatforms = -1;

    public ContextMenuBean() {
    }

    public void setRunningPlatforms(int runningPlatforms) {
        this.runningPlatforms = runningPlatforms;
    }

    public int getRunningPlatforms() {
        if (runningPlatforms == -1) {
            runningPlatforms = platformExecutionManagerBean.getRunningExecutionPlatformsCount(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
        }
        return runningPlatforms;
    }

}
