/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unacloud.paas.data.entities;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import unacloud.paas.back.dsl.Parser;
import unacloud.paas.back.execution.PlatformExecution;
import unacloud.paas.back.execution.PuppetModuleInstance;
import unacloud.paas.back.execution.RoleExecution;
/**
 *
 * @author G
 */
public class PuppetModuleUsage implements Serializable{
   int id;
   List<PuppetModuleParamValue> paramValues=new ArrayList<>();
   PuppetModule puppetmodule;
   public PuppetModuleUsage(){
   }
   public PuppetModuleUsage(int id, PuppetModule puppetmodule){
      this.id=id;
      this.puppetmodule=puppetmodule;
   }
   public int getId(){
      return id;
   }
   public void setId(int id){
      this.id=id;
   }
   public List<PuppetModuleParamValue> getParamValues(){
      return paramValues;
   }
   public void setParamValues(List<PuppetModuleParamValue> paramValues){
      this.paramValues=paramValues;
   }
   public PuppetModule getPuppetmodule(){
      return puppetmodule;
   }
   public void setPuppetmodule(PuppetModule puppetmodule){
      this.puppetmodule=puppetmodule;
   }
   @Override
   public String toString(){
      return "PuppetModuleUsage{"+"id="+id+", paramValues="+paramValues+", puppetmodule="+puppetmodule+'}';
   }
   public PuppetModuleInstance getpuppetModuleInstance(RoleExecution role, PlatformExecution platform){
      PuppetModuleInstance pmi=new PuppetModuleInstance(getPuppetmodule().getName());
      final Map<String, String> puppetValues=new TreeMap<>();
      if(getPuppetmodule().getParameters()!=null)for(PuppetModuleParam pmp : getPuppetmodule().getParameters()){
         puppetValues.put(pmp.getName(), Parser.parseFromEnvironment(pmp.getDefaultValue(),role, platform));
      }
      if(getParamValues()!=null)for(PuppetModuleParamValue pmp : getParamValues()){
         if(puppetValues.containsKey(pmp.getName())&&isValidPuppetValue(pmp.getValor())){
            puppetValues.put(pmp.getName(),Parser.parseFromEnvironment(pmp.getValor(),role,platform));
         }
      }
      for(Map.Entry<String, String> ent : puppetValues.entrySet()){
         if(ent.getKey()!=null&&!ent.getKey().trim().isEmpty()&&isValidPuppetValue(ent.getValue())){
            pmi.addValue(ent.getKey(), ent.getValue());
         }
      }
      return pmi;
   }
   private boolean isValidPuppetValue(String h){
      return h!=null&&!h.trim().isEmpty()&&!h.equalsIgnoreCase("insert");
   }
}
