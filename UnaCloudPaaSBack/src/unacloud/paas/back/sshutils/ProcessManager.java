package unacloud.paas.back.sshutils;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import unacloud.paas.back.execution.ExecutionLog;
import unacloud.paas.data.managers.PlatformExecutionManager;
public class ProcessManager{
    Process p;
    ExecutionLog log;
    public ProcessManager(ExecutionLog log, String command){
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
        PlatformExecutionManager.storeLog(log);
    }
    private void readAll(InputStream is){
        try(BufferedReader br=new BufferedReader(new InputStreamReader(is))){
            for(int c; (c=br.read())!=-1;)if(log!=null)log.appendChar((char) c);
        }catch(Exception ex){
            Logger.getLogger(ProcessManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
