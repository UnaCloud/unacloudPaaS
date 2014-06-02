/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models.enums;
/**
 *
 * @author G
 */
public enum MultiplicityType{
   ONE('1'),NONE('0'),MANY('*');
   public char id;
   private MultiplicityType(char id){
      this.id=id;
   }
   public static MultiplicityType getFromId(char id){
      if(id=='1')return ONE;
      if(id=='0')return NONE;
      if(id=='*')return MANY;
      return null;
   }
   public char getId(){
      return id;
   }
}
