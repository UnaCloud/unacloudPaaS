/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unacloud.paas.back.execution;
/**
 *
 * @author G
 */
public class ExecutionLog{
   long platformId;
   String component;
   final StringBuilder log;
   public ExecutionLog(long platformId, String component){
      this.platformId=platformId;
      this.component=component;
      log=new StringBuilder();
   }
   public ExecutionLog(long platformId, String component,String message){
      this.platformId=platformId;
      this.component=component;
      log=new StringBuilder(message);
   }
   public long getPlatformId(){
      return platformId;
   }
   public String getComponent(){
      return component;
   }
   public void appendLine(String line){
      log.append(line).append("\n");
   }
   public void appendChar(char character){
      log.append(character);
   }
   public StringBuilder getLog(){
      return log;
   }
}