package unacloud.paas.back.database.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import unacloud.paas.back.database.enums.MultiplicityType;
import unacloud.paas.back.database.enums.ResourceType;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-02-09T18:35:10")
@StaticMetamodel(MainCommand.class)
public class MainCommand_ { 

    public static volatile SingularAttribute<MainCommand, Long> id;
    public static volatile SingularAttribute<MainCommand, String> command;
    public static volatile SingularAttribute<MainCommand, String> runningUser;
    public static volatile SingularAttribute<MainCommand, MultiplicityType> multiplicity;
    public static volatile SingularAttribute<MainCommand, ResourceType> resourceType;
    public static volatile SingularAttribute<MainCommand, Long> roleId;

}