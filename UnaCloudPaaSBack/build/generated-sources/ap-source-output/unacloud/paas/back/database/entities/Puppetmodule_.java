package unacloud.paas.back.database.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import unacloud.paas.back.database.entities.PuppetModuleUsage;
import unacloud.paas.back.database.entities.Puppetparam;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-02-08T18:21:52")
@StaticMetamodel(Puppetmodule.class)
public class Puppetmodule_ { 

    public static volatile SingularAttribute<Puppetmodule, String> description;
    public static volatile SingularAttribute<Puppetmodule, String> name;
    public static volatile CollectionAttribute<Puppetmodule, PuppetModuleUsage> puppetModuleUsageCollection;
    public static volatile CollectionAttribute<Puppetmodule, Puppetparam> puppetparamCollection;

}