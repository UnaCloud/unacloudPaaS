package unacloud.paas.back.sshutils;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import unacloud.paas.back.beans.LogManagerBean;
import unacloud.paas.back.beans.PlatformExecutionManagerBean;
import unacloud.paas.back.execution.entities.RuntimeExecutionLog;
public class ProcessManager{
    Process p;
    RuntimeExecutionLog log;
    public ProcessManager(RuntimeExecutionLog log, String command){
        this.log=log;
        try{
            p=Runtime.getRuntime().exec(command);
            if(log!=null)log.appendLine("Executing:"+command);
        }catch(Exception ex){
            Logger.getLogger(ProcessManager.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ProcessManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        LogManagerBean.storeStaticLog(log);
    }
    private void readAll(InputStream is){
        try(BufferedReader br=new BufferedReader(new InputStreamReader(is))){
            for(int c; (c=br.read())!=-1;)if(log!=null)log.appendChar((char) c);
        }catch(Exception ex){
            Logger.getLogger(ProcessManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
