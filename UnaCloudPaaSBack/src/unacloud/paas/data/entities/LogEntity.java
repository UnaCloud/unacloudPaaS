/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unacloud.paas.data.entities;
import java.sql.Date;
import java.sql.Timestamp;
/**
 *
 * @author G
 */
public class LogEntity{
   long platformId;
   String component,log;
   Timestamp time;
   String relativeTime;
   public LogEntity(){
   }
   public LogEntity(long platformId, String component, String log, Timestamp time, String relativeTime){
      this.platformId=platformId;
      this.component=component;
      this.log=log;
      this.time=time;
      this.relativeTime=relativeTime;
   }
   public long getPlatformId(){
      return platformId;
   }
   public void setPlatformId(long platformId){
      this.platformId=platformId;
   }
   public String getComponent(){
      return component;
   }
   public void setComponent(String component){
      this.component=component;
   }
   public String getLog(){
      return log;
   }
   public String[] getLogLines(){
      return log.split("\n|\r");
   }
   public void setLog(String log){
      this.log=log;
   }
   public Timestamp getTime(){
      return time;
   }
   public void setTime(Timestamp time){
      this.time=time;
   }
   public String getRelativeTime(){
      return relativeTime;
   }
   public void setRelativeTime(String relativeTime){
      this.relativeTime=relativeTime;
   }
}
