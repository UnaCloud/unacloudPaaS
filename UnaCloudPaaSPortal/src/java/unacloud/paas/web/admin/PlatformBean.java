/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unacloud.paas.web.admin;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import unacloud.paas.data.entities.Platform;
import unacloud.paas.data.managers.PlatformManager;
import unacloud.paas.web.utils.AbstractEntityEditor;
/**
 *
 * @author G
 */
@ManagedBean
@ViewScoped
public class PlatformBean extends AbstractEntityEditor<Platform> implements Serializable{
   public PlatformBean(){
   }
   @Override
   public void fillList(){
      setLista(PlatformManager.getList());
   }
   @Override
   public String addNew(){
      setObject(new Platform());
      setCurrentState(ADD_NEW);
      return null;
   }
   @Override
   public List<Platform> getLista(){
      return super.getLista(); //To change body of generated methods, choose Tools | Templates.
   }
   @Override
   public Platform getObject(){
      return super.getObject(); //To change body of generated methods, choose Tools | Templates.
   }
}