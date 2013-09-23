package unacloud.paas.back.sshutils;
import java.io.File;
import unacloud.paas.back.execution.ExecutionLog;
import unacloud.paas.back.execution.NodeExecution;
public class SCP extends ProcessManager{
    File source;
    public SCP(File source, NodeExecution dest, String path,ExecutionLog log){
        super(log,"scp "+source.getAbsolutePath()+" root@"+dest.getIpAddress()+":"+path);
        this.source=source;
    }
    public File getSource(){
        return source;
    }
}