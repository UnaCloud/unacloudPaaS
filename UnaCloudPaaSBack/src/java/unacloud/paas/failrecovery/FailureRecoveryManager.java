/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unacloud.paas.failrecovery;

import java.util.List;
import unacloud.paas.back.beans.ClusterManagerBean;
import unacloud.paas.back.beans.LogManagerBean;
import unacloud.paas.back.beans.PlatformExecutionManagerBean;
import unacloud.paas.back.database.entities.Node;
import unacloud.paas.back.execution.entities.RuntimeExecutionLog;
import unacloud.paas.back.database.enums.ExecutionState;

/**
 *
 * @author G
 */
public class FailureRecoveryManager {

    public static void onMachineFailure(long platformExecutionId, List<Node> failures) {
        PlatformExecutionManagerBean.getStaticInstance().updatePlatformExecutionState(platformExecutionId, ExecutionState.RECOVERING);
        String h = "";
        for (Node node : failures) {
            h += " " + node.getHostname();
        }
        LogManagerBean.storeStaticLog(new RuntimeExecutionLog(platformExecutionId, "platform", "Platform failed on nodes " + h + ". Recovering platform."));
        ClusterManagerBean.getStaticInstance().stopCluster(platformExecutionId, ExecutionState.FAILED);
    }

    
}
