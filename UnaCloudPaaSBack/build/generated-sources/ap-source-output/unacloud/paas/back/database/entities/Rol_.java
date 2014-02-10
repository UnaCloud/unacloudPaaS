package unacloud.paas.back.database.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import unacloud.paas.back.database.entities.Platform;
import unacloud.paas.back.database.entities.PuppetModuleUsage;
import unacloud.paas.back.database.enums.MultiplicityType;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-02-09T20:37:18")
@StaticMetamodel(Rol.class)
public class Rol_ { 

    public static volatile SingularAttribute<Rol, Long> id;
    public static volatile SingularAttribute<Rol, Platform> platform;
    public static volatile SingularAttribute<Rol, Integer> imageId;
    public static volatile SingularAttribute<Rol, PuppetModuleUsage> puppetModule;
    public static volatile SingularAttribute<Rol, String> name;
    public static volatile SingularAttribute<Rol, MultiplicityType> multiplicity;
    public static volatile SingularAttribute<Rol, Boolean> mountPlatformFolder;

}