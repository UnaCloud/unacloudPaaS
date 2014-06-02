package unacloud.paas.failrecovery;

import models.enums.ExecutionState;
import unacloud.paas.back.beans.ClusterManagerBean;

public class PlatformSucessManager {

    public static void onPlatformSucess(long platformId) {
        new ClusterManagerBean().stopCluster(platformId, ExecutionState.SUCCESS);
    }
}
