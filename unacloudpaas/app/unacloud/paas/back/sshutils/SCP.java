package unacloud.paas.back.sshutils;

import models.Node;
import unacloud.paas.back.execution.entities.RuntimeExecutionLog;

import java.io.File;
public class SCP extends ProcessManager{
    File source;
    public SCP(File source, Node dest, String path,RuntimeExecutionLog log){
        super(log,"scp "+source.getAbsolutePath()+" root@"+dest.getIpAddress()+":"+path);
        this.source=source;
    }
    public File getSource(){
        return source;
    }
}