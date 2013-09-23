/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unacloud.paas.back.user;
/**
 *
 * @author G
 */
public class UserFileEntity{
   String name;
   String path;
   public UserFileEntity(){
   }
   public UserFileEntity(String name, String path){
      this.name=name;
      this.path=path;
   }
   public String getName(){
      return name;
   }
   public void setName(String name){
      this.name=name;
   }
   public String getPath(){
      return path;
   }
   public void setPath(String path){
      this.path=path;
   }
}