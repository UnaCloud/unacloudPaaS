package unacloud.paas.back.database.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import unacloud.paas.back.database.entities.Node;
import unacloud.paas.back.database.enums.ExecutionState;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-02-09T20:37:18")
@StaticMetamodel(CommandWait.class)
public class CommandWait_ { 

    public static volatile SingularAttribute<CommandWait, Long> id;
    public static volatile SingularAttribute<CommandWait, Node> node;
    public static volatile SingularAttribute<CommandWait, Long> processId;
    public static volatile SingularAttribute<CommandWait, ExecutionState> status;

}