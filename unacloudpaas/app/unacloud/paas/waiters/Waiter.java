package unacloud.paas.waiters;

import models.PlatformExecution;

public interface Waiter{
   public boolean hasEnded(final PlatformExecution platform);
}
