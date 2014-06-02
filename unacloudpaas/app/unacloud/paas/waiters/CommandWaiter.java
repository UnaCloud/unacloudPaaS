package unacloud.paas.waiters;

import models.CommandWait;
import models.Node;
import models.PlatformExecution;
import models.RolExecution;
import models.enums.ExecutionState;
import unacloud.paas.monitor.VirtualMachineMonitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandWaiter implements Waiter{
   @Override
   public boolean hasEnded(PlatformExecution platform){
      System.out.println("CommandWaiter "+platform.getId());
      for(RolExecution role : platform.getRolExecution()){
         for(Node node : role.getNodes()){
            if(!node.getWaitingCommands().isEmpty()){
               boolean canCheck=false;
               String h="";
               for(CommandWait cw : node.getWaitingCommands()){
                  cw.status=ExecutionState.SUCCESS;
                  h+=" "+cw.getProcessId();
               }
               try{
                  Process p=Runtime.getRuntime().exec("ssh root@"+node.getIpAddress()+" ps "+h);
                  try(BufferedReader br=new BufferedReader(new InputStreamReader(p.getInputStream()))){
                     String temp=br.readLine();
                     if(temp!=null&&temp.contains("PID")&&temp.contains("TTY")){
                        canCheck=true;
                     }
                     for(; (h=br.readLine())!=null;){
                        String[] j=h.trim().split("\t| +");
                        if(j[0].matches("[0-9]+")){
                           Long PID=Long.parseLong(j[0]);
                           for(CommandWait cw : node.getWaitingCommands()){
                              if(cw.getProcessId()==PID){
                                 cw.status=ExecutionState.RUNNING;
                              }
                           }
                        }
                     }
                  }
               }catch(IOException ex){
                   ex.printStackTrace();
               }
               for(CommandWait cw : node.getWaitingCommands()){
                  if(!canCheck){
                     cw.status=ExecutionState.RUNNING;
                  }
                  System.out.println("cw "+cw.getProcessId()+" "+cw.getStatus());
               }
            }
         }
      }
      boolean complete=true;
      /*try(Connection con=DatabaseConnection.getConnection(); PreparedStatement ps=con.prepareStatement("update `unacloudpaas`.`commandWait` set `commandWait`.`executionState_state`=? where `commandWait`.`processId`=? and `commandWait`.`idNode`=?;")){
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
      }*/
      return complete;
   }
}
