package unacloud.paas.back.database.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import unacloud.paas.back.database.entities.PuppetModuleUsage;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-02-08T18:21:52")
@StaticMetamodel(Puppetparamvalue.class)
public class Puppetparamvalue_ { 

    public static volatile SingularAttribute<Puppetparamvalue, Long> id;
    public static volatile SingularAttribute<Puppetparamvalue, String> valor;
    public static volatile SingularAttribute<Puppetparamvalue, String> name;
    public static volatile SingularAttribute<Puppetparamvalue, PuppetModuleUsage> puppetModuleUsageid;

}