/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unacloud.paas.failrecovery;
import java.util.List;
import unacloud.paas.back.cluster.platforms.ClusterManager;
import unacloud.paas.back.execution.ExecutionLog;
import unacloud.paas.data.entities.enums.ExecutionState;
import unacloud.paas.data.execution.NodeEntity;
import unacloud.paas.data.managers.PlatformExecutionManager;
/**
 *
 * @author G
 */
public class FailureRecoveryManager{
   public static void onMachineFailure(long platformExecutionId, List<NodeEntity> failures){
      PlatformExecutionManager.updatePlatformExecutionState(platformExecutionId, ExecutionState.RECOVERING.getId());
      String h="";
      for(NodeEntity node:failures)h+=" "+node.getHostname();
      PlatformExecutionManager.storeLog(new ExecutionLog(platformExecutionId, "platform", "Platform failed on nodes "+h+". Recovering platform."));
      ClusterManager.stopCluster(platformExecutionId,ExecutionState.FAILED);
   }
}
