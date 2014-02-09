package unacloud.paas.back.database.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import unacloud.paas.back.database.entities.ExecutionState;
import unacloud.paas.back.database.entities.Node;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-02-08T18:21:52")
@StaticMetamodel(CommandWait.class)
public class CommandWait_ { 

    public static volatile SingularAttribute<CommandWait, Long> id;
    public static volatile SingularAttribute<CommandWait, ExecutionState> executionStatestate;
    public static volatile SingularAttribute<CommandWait, Integer> processId;
    public static volatile SingularAttribute<CommandWait, Node> idNode;

}