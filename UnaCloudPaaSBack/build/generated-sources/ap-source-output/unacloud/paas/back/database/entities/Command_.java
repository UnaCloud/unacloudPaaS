package unacloud.paas.back.database.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import unacloud.paas.back.database.entities.RolExecution;
import unacloud.paas.back.database.enums.MultiplicityType;
import unacloud.paas.back.database.enums.ResourceType;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-02-09T18:35:10")
@StaticMetamodel(Command.class)
public class Command_ { 

    public static volatile SingularAttribute<Command, Long> id;
    public static volatile SingularAttribute<Command, RolExecution> rolExecution;
    public static volatile SingularAttribute<Command, String> command;
    public static volatile SingularAttribute<Command, String> runningUser;
    public static volatile SingularAttribute<Command, MultiplicityType> multiplicity;
    public static volatile SingularAttribute<Command, ResourceType> type;
    public static volatile SingularAttribute<Command, Boolean> mainCommand;

}