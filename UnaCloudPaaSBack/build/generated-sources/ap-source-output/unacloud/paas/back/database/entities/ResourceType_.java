package unacloud.paas.back.database.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import unacloud.paas.back.database.entities.Command;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-02-08T18:21:52")
@StaticMetamodel(ResourceType.class)
public class ResourceType_ { 

    public static volatile SingularAttribute<ResourceType, Integer> id;
    public static volatile SingularAttribute<ResourceType, String> description;
    public static volatile CollectionAttribute<ResourceType, Command> commandCollection;

}