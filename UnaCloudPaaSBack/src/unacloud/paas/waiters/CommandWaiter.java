/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unacloud.paas.waiters;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import unacloud.paas.back.database.DatabaseConnection;
import unacloud.paas.back.execution.ExecutionLog;
import unacloud.paas.data.entities.enums.ExecutionState;
import unacloud.paas.data.execution.CommandWaitEntity;
import unacloud.paas.data.execution.NodeEntity;
import unacloud.paas.data.execution.PlatformExecutionEntity;
import unacloud.paas.data.execution.RoleExecutionEntity;
import unacloud.paas.data.managers.PlatformExecutionManager;
import unacloud.paas.monitor.VirtualMachineMonitor;
/**
 *
 * @author G
 */
public class CommandWaiter implements Waiter{
   @Override
   public boolean hasEnded(PlatformExecutionEntity platform){
      System.out.println("CommandWaiter "+platform.getId());
      for(RoleExecutionEntity role : platform.getRoles()){
         for(NodeEntity node : role.getNodes()){
            if(!node.getWatingCommands().isEmpty()){
               boolean canCheck=false;
               String h="";
               for(CommandWaitEntity cw : node.getWatingCommands()){
                  cw.setState(ExecutionState.SUCCESS);
                  h+=" "+cw.getProcessId();
               }
               try{
                  Process p=Runtime.getRuntime().exec("ssh root@"+node.getIp()+" ps "+h);
                  try(BufferedReader br=new BufferedReader(new InputStreamReader(p.getInputStream()))){
                     String temp=br.readLine();
                     if(temp!=null&&temp.contains("PID")&&temp.contains("TTY")){
                        canCheck=true;
                     }
                     for(; (h=br.readLine())!=null;){
                        String[] j=h.trim().split("\t| +");
                        if(j[0].matches("[0-9]+")){
                           Long PID=Long.parseLong(j[0]);
                           for(CommandWaitEntity cw : node.getWatingCommands()){
                              if(cw.getProcessId()==PID){
                                 cw.setState(ExecutionState.RUNNING);
                              }
                           }
                        }
                     }
                  }
               }catch(IOException ex){
                  Logger.getLogger(VirtualMachineMonitor.class.getName()).log(Level.SEVERE, null, ex);
               }
               for(CommandWaitEntity cw : node.getWatingCommands()){
                  if(!canCheck){
                     cw.setState(ExecutionState.RUNNING);
                  }
                  System.out.println("cw "+cw.getProcessId()+" "+cw.getState());
               }
            }
         }
      }
      boolean complete=true;
      try(Connection con=DatabaseConnection.getConnection(); PreparedStatement ps=con.prepareStatement("update `unacloudpaas`.`commandWait` set `commandWait`.`executionState_state`=? where `commandWait`.`processId`=? and `commandWait`.`idNode`=?;")){
         for(RoleExecutionEntity role : platform.getRoles()){
            for(NodeEntity node : role.getNodes()){
               System.out.println(" "+node.getHostname()+" "+node.getWatingCommands().size());
               for(CommandWaitEntity cw : node.getWatingCommands()){
                  if(cw.getState()==ExecutionState.SUCCESS){
                     PlatformExecutionManager.storeLog(new ExecutionLog(platform.getId(),node.getHostname(),"Command "+cw.getProcessId()+" success"));
                     ps.setInt(1, ExecutionState.SUCCESS.getId());
                     ps.setLong(2, cw.getProcessId());
                     ps.setLong(3, cw.getNodeId());
                     ps.executeUpdate();
                  }else{
                     complete=false;
                  }
               }
            }
         }
      }catch(SQLException ex){
         Logger.getLogger(VirtualMachineMonitor.class.getName()).log(Level.SEVERE, null, ex);
      }
      return complete;
   }
}
