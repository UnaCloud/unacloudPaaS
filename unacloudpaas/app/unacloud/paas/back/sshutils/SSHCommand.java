package unacloud.paas.back.sshutils;
import models.ExecutionLog;
import models.Node;
public class SSHCommand extends ProcessManager{
    public SSHCommand(Node dest, String command, ExecutionLog log){
        super(log,"ssh root@"+dest.getIpAddress()+" "+command);
    }
}
