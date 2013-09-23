/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unacloud.paas.data.execution;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author G
 */
public class NodeEntity{
   long id;
   String hostname,ip;
   String roleName;
   int noConnectionCount=0;
   List<CommandWaitEntity> watingCommands=new ArrayList<>();
   public NodeEntity(){
   }
   public NodeEntity(long id,String hostname, String ip, String roleName){
      this.id=id;
      this.hostname=hostname;
      this.ip=ip;
      this.roleName=roleName;
   }
   public NodeEntity(String hostname, String ip){
      this.hostname=hostname;
      this.ip=ip;
   }
   public String getHostname(){
      return hostname;
   }
   public void setHostname(String hostname){
      this.hostname=hostname;
   }
   public String getIp(){
      return ip;
   }
   public void setIp(String ip){
      this.ip=ip;
   }
   public String getRoleName(){
      return roleName;
   }
   public void setRoleName(String roleName){
      this.roleName=roleName;
   }
   public int getNoConnectionCount(){
      return noConnectionCount;
   }
   public void setNoConnectionCount(int noConnectionCount){
      this.noConnectionCount=noConnectionCount;
   }
   public long getId(){
      return id;
   }
   public void setId(long id){
      this.id=id;
   }
   public List<CommandWaitEntity> getWatingCommands(){
      return watingCommands;
   }
   public void setWatingCommands(List<CommandWaitEntity> watingCommands){
      this.watingCommands=watingCommands;
   }
}