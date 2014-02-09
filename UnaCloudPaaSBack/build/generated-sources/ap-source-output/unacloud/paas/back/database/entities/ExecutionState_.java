package unacloud.paas.back.database.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import unacloud.paas.back.database.entities.CommandWait;
import unacloud.paas.back.database.entities.PlatformExecution;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-02-08T18:21:52")
@StaticMetamodel(ExecutionState.class)
public class ExecutionState_ { 

    public static volatile CollectionAttribute<ExecutionState, PlatformExecution> platformExecutionCollection;
    public static volatile SingularAttribute<ExecutionState, String> description;
    public static volatile CollectionAttribute<ExecutionState, CommandWait> commandWaitCollection;
    public static volatile SingularAttribute<ExecutionState, String> name;
    public static volatile SingularAttribute<ExecutionState, Integer> state;

}