package unacloud.paas.back.beans;

import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import unacloud.paas.back.database.entities.ExecutionLog;
import unacloud.paas.back.execution.entities.RuntimeExecutionLog;

@Singleton
@LocalBean
public class LogManagerBean {
    @PersistenceContext(unitName = "UnaCloudPaaSBackPU")
    private EntityManager em;
    public void storeLog(RuntimeExecutionLog log) {
        if (log == null)return;
        ExecutionLog dblog=new ExecutionLog(log.getPlatformId(),log.getComponent(),log.getLog().toString());
        em.merge(dblog);
    }
    public void storeLog(ExecutionLog log) {
        if (log == null) {
            return;
        }
        em.merge(log);
    }
    public static void storeStaticLog(ExecutionLog log) {
        
    }
    public static void storeStaticLog(RuntimeExecutionLog log) {
        if (log == null)return;
        ExecutionLog dblog=new ExecutionLog(log.getPlatformId(),log.getComponent(),log.getLog().toString());
        //em.merge(dblog);
    }
}
