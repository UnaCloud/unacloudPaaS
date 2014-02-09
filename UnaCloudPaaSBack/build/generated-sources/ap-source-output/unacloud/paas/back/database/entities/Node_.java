package unacloud.paas.back.database.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import unacloud.paas.back.database.entities.CommandWait;
import unacloud.paas.back.database.entities.RolExecution;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-02-08T18:21:52")
@StaticMetamodel(Node.class)
public class Node_ { 

    public static volatile CollectionAttribute<Node, CommandWait> commandWaitCollection;
    public static volatile SingularAttribute<Node, RolExecution> rolExecution;
    public static volatile SingularAttribute<Node, String> hostname;
    public static volatile SingularAttribute<Node, Integer> maxSecuentialFailCount;
    public static volatile SingularAttribute<Node, Long> idNode;
    public static volatile SingularAttribute<Node, String> iaasExecutionId;
    public static volatile SingularAttribute<Node, String> ipAddress;

}