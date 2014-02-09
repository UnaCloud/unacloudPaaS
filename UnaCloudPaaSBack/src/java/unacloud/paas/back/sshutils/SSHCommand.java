package unacloud.paas.back.sshutils;
import unacloud.paas.back.database.entities.Node;
import unacloud.paas.back.execution.entities.RuntimeExecutionLog;
public class SSHCommand extends ProcessManager{
    public SSHCommand(Node dest, String command,RuntimeExecutionLog log){
        super(log,"ssh root@"+dest.getIpAddress()+" "+command);
    }
}
