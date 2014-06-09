package unacloud.paas.back.beans;

import com.avaje.ebean.Ebean;
import models.ExecutionLog;

public class LogManagerBean {
    public static void storeLog(ExecutionLog log) {
        if (log == null)return;
        log.doFinal();
        Ebean.save(log);
    }
    public static void storeStaticLog(ExecutionLog log) {
        if (log == null){
            return;
        }
        log.doFinal();
        Ebean.save(log);
    }
}
