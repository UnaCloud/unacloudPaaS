package unacloud.paas.back.execution;
import java.util.ArrayList;
import java.util.List;
public class NodeExecution{
   private long id;
   private String hostname;
   private String ipAddress;
   private String iaasExecutionId;
   private List<PuppetModuleInstance> modules=new ArrayList<>();
   public NodeExecution(){
   }
   public NodeExecution(String hostname, String ipAddress, String iaasExecutionId){
      this.hostname=hostname;
      this.ipAddress=ipAddress;
      this.iaasExecutionId=iaasExecutionId;
   }
   public String getHostname(){
      return hostname;
   }
   public void setHostname(String hostname){
      this.hostname=hostname;
   }
   public String getIpAddress(){
      return ipAddress;
   }
   public void setIpAddress(String ipAddress){
      this.ipAddress=ipAddress;
   }
   public String getIaasExecutionId(){
      return iaasExecutionId;
   }
   public void setIaasExecutionId(String iaasExecutionId){
      this.iaasExecutionId=iaasExecutionId;
   }
   public List<PuppetModuleInstance> getModules(){
      return modules;
   }
   public long getId(){
      return id;
   }
   public void setId(long id){
      this.id=id;
   }
}
