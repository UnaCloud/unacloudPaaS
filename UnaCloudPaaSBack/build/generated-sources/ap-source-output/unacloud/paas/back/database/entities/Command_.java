package unacloud.paas.back.database.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import unacloud.paas.back.database.entities.ResourceType;
import unacloud.paas.back.database.entities.RolExecution;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-02-08T18:21:52")
@StaticMetamodel(Command.class)
public class Command_ { 

    public static volatile SingularAttribute<Command, Long> id;
    public static volatile SingularAttribute<Command, RolExecution> rolExecution;
    public static volatile SingularAttribute<Command, String> command;
    public static volatile SingularAttribute<Command, ResourceType> resourceTypeid;
    public static volatile SingularAttribute<Command, String> runningUser;
    public static volatile SingularAttribute<Command, Character> multiplicity;

}