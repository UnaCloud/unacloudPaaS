package unacloud.paas.failrecovery;

import unacloud.paas.back.beans.ClusterManagerBean;
import unacloud.paas.back.database.enums.ExecutionState;

public class PlatformSucessManager {

    public static void onPlatformSucess(long platformId) {
        ClusterManagerBean.getStaticInstance().stopCluster(platformId, ExecutionState.SUCCESS);
    }
}
