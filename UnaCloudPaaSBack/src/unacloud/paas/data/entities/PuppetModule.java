/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unacloud.paas.data.entities;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author G
 */
public class PuppetModule{
   String name;
   String descripcion;
   List<PuppetModuleParam> parameters=new ArrayList<>();
   public PuppetModule(){
   }
   public PuppetModule(String name, String descripcion){
      this.name=name;
      this.descripcion=descripcion;
   }
   public String getName(){
      return name;
   }
   public void setName(String name){
      this.name=name;
   }
   public String getDescripcion(){
      return descripcion;
   }
   public void setDescripcion(String descripcion){
      this.descripcion=descripcion;
   }
   public List<PuppetModuleParam> getParameters(){
      return parameters;
   }
   public void setParameters(List<PuppetModuleParam> parameters){
      this.parameters=parameters;
   }
   @Override
   public String toString(){
      return "PuppetModule{"+"name="+name+", descripcion="+descripcion+", parameters="+parameters+'}';
   }
}
