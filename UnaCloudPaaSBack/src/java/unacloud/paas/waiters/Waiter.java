/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unacloud.paas.waiters;
import unacloud.paas.back.database.entities.PlatformExecution;
/**
 *
 * @author G
 */
public interface Waiter{
   public boolean hasEnded(final PlatformExecution platform);
}
