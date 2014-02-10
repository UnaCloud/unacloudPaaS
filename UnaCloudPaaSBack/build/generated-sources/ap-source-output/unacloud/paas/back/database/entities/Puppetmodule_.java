package unacloud.paas.back.database.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import unacloud.paas.back.database.entities.PuppetModuleUsage;
import unacloud.paas.back.database.entities.PuppetParam;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-02-09T18:35:10")
@StaticMetamodel(PuppetModule.class)
public class PuppetModule_ { 

    public static volatile SingularAttribute<PuppetModule, String> description;
    public static volatile SingularAttribute<PuppetModule, String> name;
    public static volatile ListAttribute<PuppetModule, PuppetParam> puppetParams;
    public static volatile ListAttribute<PuppetModule, PuppetModuleUsage> puppetModuleUsage;

}