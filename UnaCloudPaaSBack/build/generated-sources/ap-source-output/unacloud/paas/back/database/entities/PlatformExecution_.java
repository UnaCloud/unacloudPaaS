package unacloud.paas.back.database.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import unacloud.paas.back.database.entities.Platform;
import unacloud.paas.back.database.entities.RolExecution;
import unacloud.paas.back.database.entities.User;
import unacloud.paas.back.database.enums.ExecutionState;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-02-09T20:37:18")
@StaticMetamodel(PlatformExecution.class)
public class PlatformExecution_ { 

    public static volatile SingularAttribute<PlatformExecution, Long> id;
    public static volatile SingularAttribute<PlatformExecution, Date> startTime;
    public static volatile SingularAttribute<PlatformExecution, String> runName;
    public static volatile SingularAttribute<PlatformExecution, Platform> platform;
    public static volatile SingularAttribute<PlatformExecution, ExecutionState> status;
    public static volatile ListAttribute<PlatformExecution, RolExecution> rolExecution;
    public static volatile SingularAttribute<PlatformExecution, User> user;
    public static volatile SingularAttribute<PlatformExecution, Boolean> eternal;

}