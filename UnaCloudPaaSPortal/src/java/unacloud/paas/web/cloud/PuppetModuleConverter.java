/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unacloud.paas.web.cloud;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import unacloud.paas.data.entities.PuppetModule;
import unacloud.paas.data.managers.PuppetModuleManager;
/**
 *
 * @author G
 */
@FacesConverter("puppetmoduleconverter")
public class PuppetModuleConverter implements Converter{
   @Override
   public Object getAsObject(FacesContext context, UIComponent component, String value){
      if(value==null||value.isEmpty())return null;
      return PuppetModuleManager.getPuppetModule(value);
   }
   @Override
   public String getAsString(FacesContext context, UIComponent component, Object value){
      if(value !=null){
         if(value instanceof PuppetModule){
            return ((PuppetModule)value).getName();
         }
      }
      return "";
   }
}
