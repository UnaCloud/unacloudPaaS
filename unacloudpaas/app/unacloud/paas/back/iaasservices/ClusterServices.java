package unacloud.paas.back.iaasservices;

import models.Node;
import unacloud.paas.back.utils.PaaSUtils;
import unacloudws.UnaCloudOperations;
import unacloudws.requests.VirtualClusterRequest;
import unacloudws.requests.VirtualImageRequest;
import unacloudws.responses.*;

import java.io.File;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClusterServices {

    static {
        new File("./utils").mkdir();
    }
    static UnaCloudOperations unacloudiaas = new UnaCloudOperations("ga.sotelo69", "NIZOQNBA4BIV5P60776O4DY7QZKGLQY5");

    public static DeployedCluster startCluster(VirtualClusterRequest request){
        System.out.println(request);
        DeploymentWS deployment = unacloudiaas.startVirtualCluster(request);
        if (deployment==null)return null;

        DeployedCluster deployedCluster=new DeployedCluster(deployment.getId());
        for(VirtualImageRequest vir:request.getVms())deployedCluster.roles.add(new DeployedClusterRol(vir.getImageId()));

        System.out.println("Levantando "+deployment.getId());
        List<VirtualMachineExecutionWS> vms=new ArrayList<>();
        ext:for (int e = 0; e < 20; e++) {
            PaaSUtils.sleep(30000);
            vms=unacloudiaas.getDeploymentInfo(deployment.getId());
            for (VirtualMachineExecutionWS vme : vms) {
                if(!(vme.getVirtualMachineExecutionStatus()==VirtualMachineStatusEnum.DEPLOYED||vme.getVirtualMachineExecutionStatus()==VirtualMachineStatusEnum.FAILED)){
                    continue ext;
                }
            }
            break;
        }
        for (VirtualMachineExecutionWS vme : vms){
            for(DeployedClusterRol dcr:deployedCluster.roles)if(dcr.id==vme.getImageId()){
                dcr.nodes.add(vme);
                break;
            }
        }
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
