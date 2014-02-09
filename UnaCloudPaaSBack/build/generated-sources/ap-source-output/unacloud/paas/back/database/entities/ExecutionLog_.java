package unacloud.paas.back.database.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import unacloud.paas.back.database.entities.PlatformExecution;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-02-08T18:21:52")
@StaticMetamodel(ExecutionLog.class)
public class ExecutionLog_ { 

    public static volatile SingularAttribute<ExecutionLog, String> message;
    public static volatile SingularAttribute<ExecutionLog, Date> time;
    public static volatile SingularAttribute<ExecutionLog, Integer> idnodeLog;
    public static volatile SingularAttribute<ExecutionLog, String> component;
    public static volatile SingularAttribute<ExecutionLog, PlatformExecution> platformExecutionid;

}