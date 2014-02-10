package unacloud.paas.back.database.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-02-09T18:35:10")
@StaticMetamodel(ExecutionLog.class)
public class ExecutionLog_ { 

    public static volatile SingularAttribute<ExecutionLog, String> message;
    public static volatile SingularAttribute<ExecutionLog, Date> tiempo;
    public static volatile SingularAttribute<ExecutionLog, Integer> idnodeLog;
    public static volatile SingularAttribute<ExecutionLog, Long> platformExecutionId;
    public static volatile SingularAttribute<ExecutionLog, String> component;

}