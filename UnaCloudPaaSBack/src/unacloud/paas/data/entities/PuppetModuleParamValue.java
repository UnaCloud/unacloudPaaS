/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unacloud.paas.data.entities;
/**
 *
 * @author G
 */
public class PuppetModuleParamValue{
   String name;
   String valor;
   public PuppetModuleParamValue(){
   }
   public PuppetModuleParamValue(String name, String valor){
      this.name=name;
      this.valor=valor;
   }
   public String getName(){
      return name;
   }
   public void setName(String name){
      this.name=name;
   }
   public String getValor(){
      return valor;
   }
   public void setValor(String valor){
      this.valor=valor;
   }
   @Override
   public String toString(){
      return "PuppetModuleParamValue{"+"name="+name+", valor="+valor+'}';
   }
}
