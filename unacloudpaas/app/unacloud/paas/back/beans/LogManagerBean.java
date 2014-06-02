package unacloud.paas.back.beans;

import com.avaje.ebean.Ebean;
import models.ExecutionLog;
import unacloud.paas.back.execution.entities.RuntimeExecutionLog;

public class LogManagerBean {
    public static void storeLog(RuntimeExecutionLog log) {
        if (log == null)return;
        ExecutionLog dblog=new ExecutionLog(log.getPlatformId(),log.getComponent(),log.getLog().toString());
        Ebean.save(dblog);
    }
    public static void storeLog(ExecutionLog log) {
        if (log == null)return;
        Ebean.save(log);
    }
    public static void storeStaticLog(ExecutionLog log) {
        
    }
    public static void storeStaticLog(RuntimeExecutionLog log) {
        if (log == null)return;
        ExecutionLog dblog=new ExecutionLog(log.getPlatformId(),log.getComponent(),log.getLog().toString());
        Ebean.save(dblog);
    }
}
