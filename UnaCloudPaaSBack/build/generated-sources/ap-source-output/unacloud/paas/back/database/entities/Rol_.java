package unacloud.paas.back.database.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import unacloud.paas.back.database.entities.Platform;
import unacloud.paas.back.database.entities.PuppetModuleUsage;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-02-08T18:21:52")
@StaticMetamodel(Rol.class)
public class Rol_ { 

    public static volatile SingularAttribute<Rol, Integer> templateId;
    public static volatile SingularAttribute<Rol, Long> id;
    public static volatile SingularAttribute<Rol, String> name;
    public static volatile SingularAttribute<Rol, PuppetModuleUsage> puppetModuleUsageid;
    public static volatile SingularAttribute<Rol, Character> multiplicity;
    public static volatile SingularAttribute<Rol, Boolean> mountPlatformFolder;
    public static volatile SingularAttribute<Rol, Platform> platformIdplatform;

}