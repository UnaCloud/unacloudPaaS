package unacloud.paas.back.database.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import unacloud.paas.back.database.entities.PuppetModule;
import unacloud.paas.back.database.entities.PuppetParamValue;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-02-09T18:35:10")
@StaticMetamodel(PuppetModuleUsage.class)
public class PuppetModuleUsage_ { 

    public static volatile SingularAttribute<PuppetModuleUsage, Integer> id;
    public static volatile SingularAttribute<PuppetModuleUsage, PuppetModule> puppetModule;
    public static volatile ListAttribute<PuppetModuleUsage, PuppetParamValue> puppetParamValue;

}