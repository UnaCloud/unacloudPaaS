package unacloud.paas.back.execution.entities;
public class RuntimeExecutionLog{
   long platformId;
   String component;
   final StringBuilder log;
   public RuntimeExecutionLog(long platformId, String component){
      this.platformId=platformId;
      this.component=component;
      log=new StringBuilder();
   }
   public RuntimeExecutionLog(long platformId, String component,String message){
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