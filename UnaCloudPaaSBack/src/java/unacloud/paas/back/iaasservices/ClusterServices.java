package unacloud.paas.back.iaasservices;

import java.io.File;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import unacloud.paas.back.utils.PaaSUtils;

import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import unacloud.paas.back.database.entities.Node;
import unacloud.paas.back.database.entities.PlatformExecution;
import unacloud.paas.back.database.entities.RolExecution;
import unacloud.paas.back.execution.RuntimePlatformExecutionBean;
import unacloud.paas.back.execution.RuntimeRoleExecutionBean;
import unacloudws.UnaCloudOperations;
import unacloudws.requests.VirtualClusterRequest;
import unacloudws.requests.VirtualImageRequest;
import unacloudws.responses.*;

public class ClusterServices {

    static {
        new File("./utils").mkdir();
    }
    static UnaCloudOperations unacloudiaas = new UnaCloudOperations("ga.sotelo69", "asdasdasd");

    public static DeployedCluster startCluster(VirtualClusterRequest request){
        String clusterIdString=unacloudiaas.startVirtualCluster(request);
        if (clusterIdString==null||!clusterIdString.matches("[0-9]+"))return null;
        long clusterId=Long.parseLong(clusterIdString);
        
        DeployedCluster deployedCluster=new DeployedCluster(clusterId);
        for(VirtualImageRequest vir:request.getVms())deployedCluster.roles.add(new DeployedClusterRol(vir.getImageId()));
        
        Logger.getLogger("PaaS").log(Level.INFO, "Levantando {0}", clusterId);
        List<VirtualMachineExecutionWS> vms;
        ext:for (int e = 0; e < 6; e++) {
            PaaSUtils.sleep(30000);
            vms=unacloudiaas.getDeploymentInfo((int)clusterId);
            for (VirtualMachineExecutionWS vme : vms) {
                if(!(vme.getVirtualMachineExecutionStatus()==VirtualMachineStatusEnum.DEPLOYED||vme.getVirtualMachineExecutionStatus()==VirtualMachineStatusEnum.FAILED)){
                    continue ext;
                }
            }
            for (VirtualMachineExecutionWS vme : vms){
                for(DeployedClusterRol dcr:deployedCluster.roles)if(dcr.id==vme.getImageId()){
                    dcr.nodes.add(vme);
                    break;
                }
            }
        }
        //Logger.getLogger("PaaS").log(Level.INFO, "startedCluster {0} {1} {2}", new Object[]{size, template, coresPerNode});
        return deployedCluster;
    }

    public static void waitForSSH(List<Node> cluster) {
        for (Node d : cluster) {
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

    

    /*public static void stopCluster(List<NodeExecution> cluster) {
        for (NodeExecution vme : cluster) {
            unacloudiaas.turnOffVirtualMachine(vme.getIaasExecutionId());
        }
    }*/
    public static void stopCluster(int clusterId) {
        unacloudiaas.stopDeployment(clusterId);
    }
}
