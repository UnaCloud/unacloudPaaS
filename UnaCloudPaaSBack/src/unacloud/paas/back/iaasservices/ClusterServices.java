package unacloud.paas.back.iaasservices;

import unacloud.paas.back.execution.NodeExecution;
import java.io.File;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import unacloud.paas.back.utils.PaaSUtils;

import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import unacloud.paas.back.execution.PlatformExecution;
import unacloud.paas.back.execution.RoleExecution;
import unacloudws.UnaCloudOperations;
import unacloudws.requests.VirtualMachineRequest;
import unacloudws.responses.*;

public class ClusterServices {

    static {
        new File("./utils").mkdir();
    }
    static UnaCloudOperations unacloudiaas = new UnaCloudOperations("ga.sotelo69", "asdasdasd");

    public static List<VirtualMachineExecutionWS> startCluster(final int template, final int size, final int coresPerNode) {
        String clusterIdString=unacloudiaas.startVirtualCluster(template,1024,new VirtualMachineRequest(size,coresPerNode*1024,coresPerNode,1));
        int clusterId=Integer.parseInt(clusterIdString);
        List<VirtualMachineExecutionWS> aEncender = unacloudiaas.getDeploymentInfo(clusterId);
        Logger.getLogger("PaaS").log(Level.INFO, "Levantando {0}", aEncender.size());

        if (aEncender.isEmpty())return new ArrayList<>();
        ext:for (int e = 0; e < 4; e++) {
            PaaSUtils.sleep(30000);
            List<VirtualMachineExecutionWS> vms=unacloudiaas.getDeploymentInfo(clusterId);
            for (VirtualMachineExecutionWS vme : vms) {
                if(vme.getVirtualMachineExecutionStatus()!=VirtualMachineStatusEnum.DEPLOYED){
                    continue ext;
                }
            }
            return vms;
        }
        Logger.getLogger("PaaS").log(Level.INFO, "startedCluster {0} {1} {2}", new Object[]{size, template, coresPerNode});
        return new ArrayList<>();
    }

    public static void waitForSSH(List<NodeExecution> cluster) {
        for (NodeExecution d : cluster) {
            while (true) {
                try {
                    Socket s = new Socket(d.getIpAddress(), 22);
                    s.close();
                    break;
                } catch (Exception e) {
                    System.err.println("No esta ssh arriba " + d.getHostname());
                    PaaSUtils.sleep(20000);
                }
            }
        }
    }

    public static void configureHostTable(PlatformExecution cluster) {
        File f = new File("./utils/hostable" + cluster.getPlatformExecutionId());
        new File("./utils").mkdir();
        try (PrintWriter pw = new PrintWriter(f)) {
            pw.println("127.0.0.1\tlocalhost");
            pw.println("157.253.236.162\tpuppetmaster");
            for (RoleExecution role : cluster.getRoles()) {
                for (NodeExecution d : role.getNodes()) {
                    pw.println(d.getIpAddress() + "\t" + d.getHostname());
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ClusterServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (RoleExecution role : cluster.getRoles()) {
            role.sendFileToRole(f, "/etc/hosts");
        }
        f.delete();
    }

    /*public static void stopCluster(List<NodeExecution> cluster) {
        for (NodeExecution vme : cluster) {
            unacloudiaas.turnOffVirtualMachine(vme.getIaasExecutionId());
        }
    }*/
    public static void stopCluster(int clusterId) {
        unacloudiaas.stopDeployment(clusterId);
    }
}
