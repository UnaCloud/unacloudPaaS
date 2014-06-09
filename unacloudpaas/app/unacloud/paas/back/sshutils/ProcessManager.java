package unacloud.paas.back.sshutils;

import models.ExecutionLog;
import unacloud.paas.back.beans.LogManagerBean;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
public class ProcessManager{
    Process p;
    ExecutionLog log;
    public ProcessManager(ExecutionLog log, String command){
        this.log=log;
        try{
            System.out.println("Exec: "+command);
            p=Runtime.getRuntime().exec(command);
            if(log!=null)log.appendLine("Executing:"+command);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public InputStream getInputStream(){
       return p.getInputStream();
    }
    public void waitFor(){
        readAll(p.getInputStream());
        readAll(p.getErrorStream());
        try{
            if(p!=null)p.waitFor();
        }catch(InterruptedException ex){
            ex.printStackTrace();
        }
        LogManagerBean.storeStaticLog(log);
    }
    private void readAll(InputStream is){
        try(BufferedReader br=new BufferedReader(new InputStreamReader(is))){
            for(int c; (c=br.read())!=-1;)if(log!=null)log.appendChar((char) c);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
