/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unacloud.paas.waiters;
import unacloud.paas.data.execution.PlatformExecutionEntity;
/**
 *
 * @author G
 */
public interface Waiter{
   public boolean hasEnded(final PlatformExecutionEntity platform);
}
