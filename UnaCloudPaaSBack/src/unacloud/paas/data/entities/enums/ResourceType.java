/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unacloud.paas.data.entities.enums;
/**
 *
 * @author G
 */
public enum ResourceType{
   LOCAL(1),GLOBAL(2);
   int value;
   private ResourceType(int value){
      this.value=value;
   }
   public static ResourceType getFromId(int id){
      if(id==2)return GLOBAL;
      return LOCAL;
   }
   public int getValue(){
      return value;
   }
   
}