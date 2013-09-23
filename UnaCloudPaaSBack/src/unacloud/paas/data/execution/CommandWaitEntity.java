/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unacloud.paas.data.execution;
import unacloud.paas.data.entities.enums.ExecutionState;
/**
 *
 * @author G
 */
public class CommandWaitEntity{
   long nodeId;
   long processId;
   ExecutionState state;
   public CommandWaitEntity(){
   }
   public CommandWaitEntity(long nodeId, long processId, ExecutionState state){
      this.nodeId=nodeId;
      this.processId=processId;
      this.state=state;
   }
   public CommandWaitEntity(long nodeId, long processId){
      this.nodeId=nodeId;
      this.processId=processId;
   }
   public long getNodeId(){
      return nodeId;
   }
   public void setNodeId(long nodeId){
      this.nodeId=nodeId;
   }
   public long getProcessId(){
      return processId;
   }
   public void setProcessId(long processId){
      this.processId=processId;
   }
   public ExecutionState getState(){
      return state;
   }
   public void setState(ExecutionState state){
      this.state=state;
   }
}
