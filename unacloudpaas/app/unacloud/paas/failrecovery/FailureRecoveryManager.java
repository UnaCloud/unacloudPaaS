package unacloud.paas.failrecovery;

import models.ExecutionLog;
import models.Node;
import models.PlatformExecution;
import models.enums.ExecutionState;
import unacloud.paas.back.beans.ClusterManagerBean;
import unacloud.paas.back.beans.LogManagerBean;
import unacloud.paas.back.beans.PlatformExecutionManagerBean;

import java.util.List;

public class FailureRecoveryManager {

    public static void onMachineFailure(PlatformExecution platformExecution, List<Node> failures) {
        new PlatformExecutionManagerBean().updatePlatformExecutionState(platformExecution.id, ExecutionState.RECOVERING);
        String h = "";
        for (Node node : failures) {
            h += " " + node.getHostname();
        }
        LogManagerBean.storeStaticLog(new ExecutionLog(platformExecution.id, null, "platform", "Platform failed on nodes " + h + ". Recovering platform."));
        new ClusterManagerBean().stopCluster(platformExecution, ExecutionState.FAILED);
    }

    
}
