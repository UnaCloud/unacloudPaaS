/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unacloud.paas.monitor;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import unacloud.paas.back.database.DatabaseConnection;
import unacloud.paas.back.utils.PaaSUtils;
import unacloud.paas.data.entities.enums.ExecutionState;
import unacloud.paas.data.execution.NodeEntity;
import unacloud.paas.data.execution.PlatformExecutionEntity;
import unacloud.paas.data.execution.RoleExecutionEntity;
import unacloud.paas.data.managers.PlatformExecutionManager;
import unacloud.paas.failrecovery.FailureRecoveryManager;
import unacloud.paas.waiters.WaiterManager;
/**
 *
 * @author G
 */
public class VirtualMachineMonitor extends Thread{
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
         List<PlatformExecutionEntity> platforms=getUsedVirtualMachines();
         if(!platforms.isEmpty()){
            checkFailure(platforms);
            checkTermination(platforms);
         }
      }
   }
   private void checkFailure(List<PlatformExecutionEntity> platforms){
      checkSSH(platforms);
      for(int p=0; p<platforms.size(); p++){
         final PlatformExecutionEntity platform=platforms.get(p);
         for(RoleExecutionEntity role : platform.getRoles()){
            List<NodeEntity> failedNodes=new ArrayList<>();
            for(NodeEntity node : role.getNodes()){
               if(node.getNoConnectionCount()>=MAX_FAIL){
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
   private void checkSSH(List<PlatformExecutionEntity> platforms){
      for(PlatformExecutionEntity platform : platforms){
         for(RoleExecutionEntity role : platform.getRoles()){
            for(NodeEntity node : role.getNodes()){
               try(Socket s=new Socket();){
                  s.connect(new InetSocketAddress(node.getIp(), 22), 6000);
                  node.setNoConnectionCount(0);
               }catch(IOException ex){
                  node.setNoConnectionCount(node.getNoConnectionCount()+1);
               }
            }
         }
      }
      try(Connection con=DatabaseConnection.getConnection(); PreparedStatement ps=con.prepareStatement("update `unacloudpaas`.`node` set `node`.`maxSecuentialFailCount` = ? where `node`.`hostname`=? and `node`.`platformExecution_id`=? and `node`.`roleExecution_name`=?;");){
         for(PlatformExecutionEntity platform : platforms){
            for(RoleExecutionEntity role : platform.getRoles()){
               for(NodeEntity node : role.getNodes()){
                  ps.setInt(1, node.getNoConnectionCount());
                  ps.setString(2, node.getHostname());
                  ps.setLong(3, platform.getId());
                  ps.setString(4, node.getRoleName());
                  ps.addBatch();
               }
            }
         }
         ps.executeBatch();
      }catch(SQLException ex){
         Logger.getLogger(VirtualMachineMonitor.class
                 .getName()).log(Level.SEVERE, null, ex);
      }
   }
   private void checkTermination(List<PlatformExecutionEntity> platforms){
      for(PlatformExecutionEntity platform : platforms){
         if(platform.getPlatform().getWaiterClass()!=null){
            WaiterManager.checkTermination(platform);
         }
      }
      
   }
   private List<PlatformExecutionEntity> getUsedVirtualMachines(){
      List<PlatformExecutionEntity> platforms=new ArrayList<>();
      try(Connection con=DatabaseConnection.getConnection(); Statement st=con.createStatement();){
         try(ResultSet rs=st.executeQuery("SELECT `platformExecution`.`id` FROM `unacloudpaas`.`platformExecution` where `platformExecution`.`executionState_state`="+ExecutionState.RUNNING.getId()+" and `eternal`=0;");){
            while(rs.next())
               platforms.add(PlatformExecutionManager.getPlatformExecution(rs.getLong(1)));
         }
      }catch(SQLException ex){
         Logger.getLogger(VirtualMachineMonitor.class.getName()).log(Level.SEVERE, null, ex);
      }
      return platforms;
   }
}