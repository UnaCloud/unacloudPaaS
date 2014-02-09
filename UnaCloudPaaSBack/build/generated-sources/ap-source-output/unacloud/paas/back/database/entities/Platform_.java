package unacloud.paas.back.database.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import unacloud.paas.back.database.entities.PlatformExecution;
import unacloud.paas.back.database.entities.Rol;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-02-08T18:21:52")
@StaticMetamodel(Platform.class)
public class Platform_ { 

    public static volatile CollectionAttribute<Platform, PlatformExecution> platformExecutionCollection;
    public static volatile CollectionAttribute<Platform, Rol> rolCollection;
    public static volatile SingularAttribute<Platform, String> name;
    public static volatile SingularAttribute<Platform, String> waiterClass;
    public static volatile SingularAttribute<Platform, Integer> idplatform;
    public static volatile SingularAttribute<Platform, String> imagename;

}