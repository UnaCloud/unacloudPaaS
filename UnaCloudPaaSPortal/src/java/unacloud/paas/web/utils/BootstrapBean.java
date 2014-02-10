/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unacloud.paas.web.utils;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import unacloud.paas.back.beans.InitialDeploymentBean;


/**
 *
 * @author G
 */
@ManagedBean
@RequestScoped
public class BootstrapBean {
    
    @EJB
    private InitialDeploymentBean initialDeploymentBean;
    
    /**
     * Creates a new instance of Bootstrap
     */
    public BootstrapBean() {
    }
    public String doBootstrap(){
        initialDeploymentBean.doInitialDeployment();
        return null;
    }
}
