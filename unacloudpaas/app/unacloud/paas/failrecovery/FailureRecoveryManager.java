package unacloud.paas.failrecovery;

import models.Node;
import models.enums.ExecutionState;
import unacloud.paas.back.beans.ClusterManagerBean;
import unacloud.paas.back.beans.LogManagerBean;
import unacloud.paas.back.beans.PlatformExecutionManagerBean;
import unacloud.paas.back.execution.entities.RuntimeExecutionLog;

import java.util.List;

public class FailureRecoveryManager {

    public static void onMachineFailure(long platformExecutionId, List<Node> failures) {
        new PlatformExecutionManagerBean().updatePlatformExecutionState(platformExecutionId, ExecutionState.RECOVERING);
        String h = "";
        for (Node node : failures) {
            h += " " + node.getHostname();
        }
        LogManagerBean.storeStaticLog(new RuntimeExecutionLog(platformExecutionId, "platform", "Platform failed on nodes " + h + ". Recovering platform."));
        new ClusterManagerBean().stopCluster(platformExecutionId, ExecutionState.FAILED);
    }

    
}
