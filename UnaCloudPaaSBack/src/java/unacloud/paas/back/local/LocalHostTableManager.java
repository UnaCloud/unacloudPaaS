package unacloud.paas.back.local;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import unacloud.paas.back.database.entities.Node;
import unacloud.paas.back.database.entities.PlatformExecution;
import unacloud.paas.back.database.entities.RolExecution;

public class LocalHostTableManager{
    private static final Map<String, String> nameIp=new TreeMap<>();
    private static final Map<String, String> ipName=new TreeMap<>();
    private static final String newLine=System.getProperty("line.separator");
    static{
        addHost("localhost", "127.0.0.1");
        addHost("puppetmaster", "157.253.236.162");
    }
    private static void addHost(String name, String ip){
        String t=nameIp.remove(name);
        if(t!=null){
            ipName.remove(t);
        }
        t=ipName.remove(ip);
        if(t!=null){
            nameIp.remove(t);
        }
        nameIp.put(name, ip);
        ipName.put(ip, name);
    }
    public static synchronized void addPlatformHosts(PlatformExecution platform){
        for(RolExecution role:platform.getRolExecution())for(Node n : role.getNodes())addHost(n.getHostname(), n.getIpAddress());
        StringBuilder sb=new StringBuilder();
        for(Entry<String, String> entry : ipName.entrySet())sb.append(entry.getKey()).append("\t").append(entry.getValue()).append(newLine);
        try(FileWriter fw=new FileWriter(new File("/etc/hosts"))){
            fw.write(sb.toString());
        }catch(IOException ex){
            Logger.getLogger(LocalHostTableManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
