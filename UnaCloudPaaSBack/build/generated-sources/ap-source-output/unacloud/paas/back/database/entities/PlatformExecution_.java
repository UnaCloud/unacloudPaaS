package unacloud.paas.back.database.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import unacloud.paas.back.database.entities.ExecutionLog;
import unacloud.paas.back.database.entities.ExecutionState;
import unacloud.paas.back.database.entities.Platform;
import unacloud.paas.back.database.entities.RolExecution;
import unacloud.paas.back.database.entities.User;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-02-08T18:21:52")
@StaticMetamodel(PlatformExecution.class)
public class PlatformExecution_ { 

    public static volatile SingularAttribute<PlatformExecution, Long> id;
    public static volatile SingularAttribute<PlatformExecution, Date> startTime;
    public static volatile SingularAttribute<PlatformExecution, String> runName;
    public static volatile CollectionAttribute<PlatformExecution, ExecutionLog> executionLogCollection;
    public static volatile SingularAttribute<PlatformExecution, ExecutionState> executionStatestate;
    public static volatile SingularAttribute<PlatformExecution, User> userUsername;
    public static volatile CollectionAttribute<PlatformExecution, RolExecution> rolExecutionCollection;
    public static volatile SingularAttribute<PlatformExecution, Boolean> eternal;
    public static volatile SingularAttribute<PlatformExecution, Platform> platformIdplatform;

}