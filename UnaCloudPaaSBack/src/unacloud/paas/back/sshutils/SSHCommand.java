package unacloud.paas.back.sshutils;
import unacloud.paas.back.execution.ExecutionLog;
import unacloud.paas.back.execution.NodeExecution;
public class SSHCommand extends ProcessManager{
    
    public SSHCommand(NodeExecution dest, String command,ExecutionLog log){
        super(log,"ssh root@"+dest.getIpAddress()+" "+command);
    }
}
