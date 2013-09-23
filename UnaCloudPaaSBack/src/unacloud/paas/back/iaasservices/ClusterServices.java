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
import unacloud.iaas.ws.UnaCloudOperations;
import unacloud.iaas.ws.production.VirtualMachineExecutionWS;
import unacloud.paas.back.execution.PlatformExecution;
import unacloud.paas.back.execution.RoleExecution;
public class ClusterServices{
   static{
      new File("./utils").mkdir();
   }
   static UnaCloudOperations unacloudiaas=new UnaCloudOperations("ga.sotelo69", "asdasdasd");
   public static List<VirtualMachineExecutionWS> startCluster(final int template, final int size, final int coresPerNode){
      
      final Set<String> executionIds=new TreeSet<>();
      final Set<VirtualMachineExecutionWS> encendidas=new TreeSet<>(new Comparator<VirtualMachineExecutionWS>(){
         @Override
         public int compare(VirtualMachineExecutionWS o1, VirtualMachineExecutionWS o2){
            return o1.getVirtualMachineExecutionCode().compareTo(o2.getVirtualMachineExecutionCode());
         }
      });
      
      while(encendidas.size()<size){
         List<VirtualMachineExecutionWS> aEncender=unacloudiaas.turnOnVirtualCluster(template, size, coresPerNode*1024, coresPerNode, 20,1024);
         if(aEncender.isEmpty()){
            return new ArrayList<>();
         }
         for(VirtualMachineExecutionWS vme : aEncender){
            executionIds.add(vme.getVirtualMachineExecutionCode());
         }
         ciclo:
         for(int e=0; e<4; e++){
            PaaSUtils.sleep(30000);
            for(VirtualMachineExecutionWS vme : unacloudiaas.getVirtualMachineExecutions(template)){
               if(executionIds.contains(vme.getVirtualMachineExecutionCode())&&vme.getVirtualMachineExecutionStatus()==1){
                  executionIds.remove(vme.getVirtualMachineExecutionCode());
                  encendidas.add(vme);
               }
            }
            if(encendidas.size()>=size)break;
         }
      }
      System.out.println("startCluster "+size+" "+template+" "+coresPerNode);
      for(VirtualMachineExecutionWS vm:encendidas)System.out.println("  "+vm);
      return new ArrayList<>(encendidas);
   }
   public static void waitForSSH(List<NodeExecution> cluster){
      for(NodeExecution d : cluster){
         while(true)
            try{
               Socket s=new Socket(d.getIpAddress(), 22);
               s.close();
               break;
            }catch(Exception e){
               System.err.println("No esta ssh arriba "+d.getHostname());
               PaaSUtils.sleep(20000);
            }
      }
   }
   public static void configureHostTable(PlatformExecution cluster){
      File f=new File("./utils/hostable"+cluster.getPlatformExecutionId());
      new File("./utils").mkdir();
      try(PrintWriter pw=new PrintWriter(f)){
         pw.println("127.0.0.1\tlocalhost");
         pw.println("157.253.236.162\tpuppetmaster");
         for(RoleExecution role : cluster.getRoles()){
            for(NodeExecution d : role.getNodes()){
               pw.println(d.getIpAddress()+"\t"+d.getHostname());
            }
         }
      }catch(FileNotFoundException ex){
         Logger.getLogger(ClusterServices.class.getName()).log(Level.SEVERE, null, ex);
      }
      for(RoleExecution role : cluster.getRoles()){
         role.sendFileToRole(f, "/etc/hosts");
      }
      f.delete();
   }
   public static void stopCluster(List<NodeExecution> cluster){
      for(NodeExecution vme : cluster){
         unacloudiaas.turnOffVirtualMachine(vme.getIaasExecutionId());
      }
   }
}
