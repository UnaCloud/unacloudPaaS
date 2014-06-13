package unacloud.paas.waiters;

import com.avaje.ebean.Ebean;
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
                            if(temp!=null&&temp.contains("PID")&&temp.contains("TTY")){
                                canCheck=true;
                            }
                            for(String h; (h=br.readLine())!=null;){
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
                    }
                }
            }
        }
        boolean complete=true;
        try{
            for(RolExecution role : platform.rolExecution){
                for(Node node : role.getNodes()){
                    for(CommandWait cw : node.waitingCommands){
                        if(cw.status==ExecutionState.SUCCESS){
                            LogManagerBean.storeLog(new ExecutionLog(platform.getId(), node.getId(),node.getHostname(), "Command " + cw.getProcessId() + " success"));
                            Ebean.update(cw);
                        }else{
                            complete=false;
                        }
                    }
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return complete;
    }
}
