package unacloud.paas.waiters;

import models.*;
import models.enums.ExecutionState;
import unacloud.paas.back.beans.LogManagerBean;
import unacloud.paas.back.beans.PlatformExecutionManagerBean;
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
               String args="";
               for(CommandWait cw : node.getWaitingCommands()){
                  cw.status=ExecutionState.SUCCESS;
                  args+=" "+cw.getProcessId();
               }
                try{
                    Process p=Runtime.getRuntime().exec("ssh root@"+node.getIpAddress()+" ps "+args);
                    try(BufferedReader br=new BufferedReader(new InputStreamReader(p.getInputStream()))){
                        String temp=br.readLine();
                        System.out.println(temp);
                        if(temp!=null&&temp.contains("PID")&&temp.contains("TTY")){
                            canCheck=true;
                        }
                        for(String h; (h=br.readLine())!=null;){
                            System.out.println(h);
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
                        System.out.println("canCheck "+canCheck);
                        cw.status=ExecutionState.RUNNING;
                    }
                    System.out.println("cw "+cw.getProcessId()+" "+cw.getStatus());
                }
            }
         }
      }
        boolean complete=true;
        try/*(Connection con=DatabaseConnection.getConnection(); PreparedStatement ps=con.prepareStatement("update `unacloudpaas`.`commandWait` set `commandWait`.`executionState_state`=? where `commandWait`.`processId`=? and `commandWait`.`idNode`=?;"))*/{
            for(RolExecution role : platform.rolExecution){
                for(Node node : role.getNodes()){
                    System.out.println(" "+node.getHostname()+" "+node.waitingCommands.size());
                    for(CommandWait cw : node.waitingCommands){
                        System.out.println("  "+cw.getId()+" "+cw.status);
                        if(cw.status==ExecutionState.SUCCESS){
                            LogManagerBean.storeLog(new ExecutionLog(platform.getId(), node.getId(),node.getHostname(), "Command " + cw.getProcessId() + " success"));
                            /*ps.setInt(1, ExecutionState.SUCCESS.getId());
                            ps.setLong(2, cw.getProcessId());
                            ps.setLong(3, cw.getNodeId());
                            ps.executeUpdate();*/
                        }else{
                            complete=false;
                        }
                    }
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        System.out.println("Complete = "+complete);
        return complete;
   }
}
