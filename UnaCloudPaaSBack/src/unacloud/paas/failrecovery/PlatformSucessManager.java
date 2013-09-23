/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unacloud.paas.failrecovery;
import unacloud.paas.back.cluster.platforms.ClusterManager;
import unacloud.paas.data.entities.enums.ExecutionState;
/**
 *
 * @author G
 */
public class PlatformSucessManager{
   public static void onPlatformSucess(long platformId){
      ClusterManager.stopCluster(platformId, ExecutionState.SUCCESS);
   }
}
