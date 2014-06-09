package unacloud.paas.back.sshutils;

import models.ExecutionLog;
import models.Node;

import java.io.File;
public class SCP extends ProcessManager{
    File source;
    public SCP(File source, Node dest, String path, ExecutionLog log){
        super(log,"scp "+source.getAbsolutePath()+" root@"+dest.getIpAddress()+":"+path);
        this.source=source;
    }
    public File getSource(){
        return source;
    }
}