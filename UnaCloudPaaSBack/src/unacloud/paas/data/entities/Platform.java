/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unacloud.paas.data.entities;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author G
 */
public class Platform implements Serializable{
   String name;
   int id;
   String image;
   String waiterClass;
   List<PlatformRole> roles=new ArrayList<>();
   List<String[]> sshSharedKeys=new ArrayList<>();
   MainCommand mainCommand;
   public Platform(){
   }
   public Platform(String name, int id, String image, String waiterClass){
      this.name=name;
      this.id=id;
      this.image=image;
      this.waiterClass=waiterClass;
   }
   public String getName(){
      return name;
   }
   public void setName(String name){
      this.name=name;
   }
   public int getId(){
      return id;
   }
   public void setId(int id){
      this.id=id;
   }
   public List<PlatformRole> getRoles(){
      return roles;
   }
   public void setRoles(List<PlatformRole> roles){
      this.roles=roles;
   }
   public List<String[]> getSshSharedKeys(){
      return sshSharedKeys;
   }
   public String getImage(){
      return image;
   }
   public void setImage(String image){
      this.image=image;
   }
   @Override
   public String toString(){
      return "Platform{"+"name="+name+", id="+id+", \n\troles="+roles+'}';
   }
   public MainCommand getMainCommand(){
      return mainCommand;
   }
   public void setMainCommand(MainCommand mainCommand){
      this.mainCommand=mainCommand;
   }
   public String getWaiterClass(){
      return waiterClass;
   }
   public void setWaiterClass(String waiterClass){
      this.waiterClass=waiterClass;
   }
}