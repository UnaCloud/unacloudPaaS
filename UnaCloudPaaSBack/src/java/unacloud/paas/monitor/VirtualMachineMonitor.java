/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unacloud.paas.monitor;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import unacloud.paas.back.database.entities.Node;
import unacloud.paas.back.database.entities.PlatformExecution;
import unacloud.paas.back.database.entities.RolExecution;
import unacloud.paas.back.utils.PaaSUtils;
import unacloud.paas.back.database.enums.ExecutionState;
import unacloud.paas.failrecovery.FailureRecoveryManager;
import unacloud.paas.waiters.WaiterManager;
/**
 *
 * @author G
 */
public class VirtualMachineMonitor extends Thread{
    @PersistenceContext()
    private EntityManager entityManager;
   private static final int MAX_FAIL=3;
   private static VirtualMachineMonitor monitor;
   private static final long WAIT_TIME=60000;
   public synchronized static void startMonitor(){
      System.out.println("startMonitor");
      if(monitor==null){
         (monitor=new VirtualMachineMonitor()).start();
      }
   }
   @Override
   public void run(){
      Set<Long> failedPlaftorms=new HashSet<>();
      while(true){
         failedPlaftorms.clear();
         PaaSUtils.sleep(((System.currentTimeMillis()/WAIT_TIME)+1)*WAIT_TIME-System.currentTimeMillis());
         List<PlatformExecution> platforms=getUsedVirtualMachines();
         if(!platforms.isEmpty()){
            checkFailure(platforms);
            checkTermination(platforms);
         }
      }
   }
   private void checkFailure(List<PlatformExecution> platforms){
      checkSSH(platforms);
      for(int p=0; p<platforms.size(); p++){
         final PlatformExecution platform=platforms.get(p);
         for(RolExecution role : platform.getRolExecution()){
            List<Node> failedNodes=new ArrayList<>();
            for(Node node : role.getNodes()){
               if(node.getMaxSecuentialFailCount()>=MAX_FAIL){
                  failedNodes.add(node);
               }
            }
            if(!failedNodes.isEmpty()){
               platforms.remove(p--);
               FailureRecoveryManager.onMachineFailure(platform.getId(), failedNodes);
            }
         }
      }
   }
   private void checkSSH(List<PlatformExecution> platforms){
       Query q=entityManager.createQuery("UPDATE Node n SET n.maxSecuentialFailCount = :maxSecuentialFailCount WHERE n.id = :id");
       List<Node> changes=new LinkedList<>();
      for(PlatformExecution platform : platforms){
         for(RolExecution role : platform.getRolExecution()){
            for(Node node : role.getNodes()){
               try(Socket s=new Socket();){
                  s.connect(new InetSocketAddress(node.getIpAddress(), 22), 6000);
                  if(node.getMaxSecuentialFailCount()!=0){
                      node.setMaxSecuentialFailCount(0);
                      changes.add(node);
                  }
               }catch(IOException ex){
                  node.setMaxSecuentialFailCount(node.getMaxSecuentialFailCount()+1);
                  changes.add(node);
               }
            }
         }
      }
        for(Node node : changes){
            q.setParameter("id", node.getId());
            q.setParameter("maxSecuentialFailCount",node.getMaxSecuentialFailCount());
            q.executeUpdate();
        }
   }
   private void checkTermination(List<PlatformExecution> platforms){
      for(PlatformExecution platform : platforms){
         if(platform.getPlatform().getWaiterClass()!=null){
            WaiterManager.checkTermination(platform);
         }
      }
      
   }
   private List<PlatformExecution> getUsedVirtualMachines(){
       TypedQuery<PlatformExecution> query=entityManager.createNamedQuery("PlatformExecution.findByEternal",PlatformExecution.class);
       query.setParameter("state", ExecutionState.RUNNING);
       return query.getResultList();
   }
}