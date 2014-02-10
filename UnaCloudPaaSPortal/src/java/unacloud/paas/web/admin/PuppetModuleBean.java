/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unacloud.paas.web.admin;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import org.primefaces.context.RequestContext;
import unacloud.paas.back.beans.PuppetModuleManagerBean;
import unacloud.paas.back.database.entities.PuppetModule;
import unacloud.paas.back.database.entities.PuppetParam;

/**
 *
 * @author G
 */
@ManagedBean
@ViewScoped
public class PuppetModuleBean /*extends AbstractEntityEditor<PuppetModule>*/ {

    @EJB
    PuppetModuleManagerBean puppetModuleManagerBean;
    
    List<PuppetModule> modules;
    PuppetModule insert = new PuppetModule();
    boolean update = false;

    public PuppetModuleBean() {
    }

    public List<PuppetModule> getModules() {
        if (modules == null) {
            modules = puppetModuleManagerBean.getPuppetModules();
        }
        return modules;
    }

    public void setModules(List<PuppetModule> modules) {
        this.modules = modules;
    }

    public PuppetModule getInsert() {
        return insert;
    }

    public void setInsert(PuppetModule insert) {
        this.insert = insert;
    }

    public String edit(PuppetModule pm) {
        update = true;
        insert = pm;
        //puppetModuleBean.fillPuppetModule(insert);
        return null;
    }

    public String addParam() {
        if (insert.getPuppetParams() == null) {
            insert.setPuppetParams(new ArrayList<PuppetParam>());
        }
        insert.getPuppetParams().add(new PuppetParam("Insert", "Insert"));
        return null;
    }

    public String insertModule() {
        if (insert.getName() == null || insert.getName().trim().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage("valid", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid name", ""));
        } else {
            if (!update) {
                puppetModuleManagerBean.insertPuppetModule(insert);
                {
                    RequestContext.getCurrentInstance().execute("panelAdd.hide();");
                    insert = new PuppetModule();
                }/* else {
                    FacesContext.getCurrentInstance().addMessage("valid", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error on insert", ""));
                }*/
            } else {
                puppetModuleManagerBean.updatePuppetModule(insert);
                {
                    RequestContext.getCurrentInstance().execute("panelAdd.hide();");
                    insert = new PuppetModule();
                }/* else {
                    FacesContext.getCurrentInstance().addMessage("valid", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error on update", ""));
                }*/
            }
        }
        return null;
    }

    public String clear() {
        update = false;
        insert = new PuppetModule();
        return null;
    }
}
