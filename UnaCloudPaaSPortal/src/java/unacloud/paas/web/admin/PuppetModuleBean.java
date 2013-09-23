/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unacloud.paas.web.admin;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import unacloud.paas.data.entities.PuppetModule;
import unacloud.paas.data.entities.PuppetModuleParam;
import unacloud.paas.data.managers.PuppetModuleManager;
/**
 *
 * @author G
 */
@ManagedBean
@ViewScoped
public class PuppetModuleBean /*extends AbstractEntityEditor<PuppetModule>*/{
    List<PuppetModule> modules;
    PuppetModule insert=new PuppetModule();
    boolean update=false;
    public PuppetModuleBean(){
    }
    public List<PuppetModule> getModules(){
        if(modules==null){
            modules=PuppetModuleManager.getPuppetModules();
        }
        return modules;
    }
    public void setModules(List<PuppetModule> modules){
        this.modules=modules;
    }
    public PuppetModule getInsert(){
        return insert;
    }
    public void setInsert(PuppetModule insert){
        this.insert=insert;
    }
    public String edit(PuppetModule pm){
        update=true;
        insert=pm;
        PuppetModuleManager.fillPuppetModule(insert);
        return null;
    }
    public String addParam(){
        if(insert.getParameters()==null){
            insert.setParameters(new ArrayList<PuppetModuleParam>());
        }
        insert.getParameters().add(new PuppetModuleParam("Insert", "Insert"));
        return null;
    }
    public String insertModule(){
        if(insert.getName()==null||insert.getName().trim().isEmpty()){
            FacesContext.getCurrentInstance().addMessage("valid", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid name", ""));
        }else{
            if(!update){
                if(PuppetModuleManager.insertPuppetModule(insert)){
                    RequestContext.getCurrentInstance().execute("panelAdd.hide();");
                    insert=new PuppetModule();
                }else FacesContext.getCurrentInstance().addMessage("valid", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error on insert", ""));
            }else{
                if(PuppetModuleManager.updatePuppetModule(insert)){
                    RequestContext.getCurrentInstance().execute("panelAdd.hide();");
                    insert=new PuppetModule();
                }else FacesContext.getCurrentInstance().addMessage("valid", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error on update", ""));
            }
        }
        return null;
    }
    public String clear(){
        update=false;
        insert=new PuppetModule();
        return null;
    }
}
