package unacloud.paas.back.database.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import unacloud.paas.back.database.entities.PuppetModuleUsage;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-02-09T20:37:18")
@StaticMetamodel(PuppetParamValue.class)
public class PuppetParamValue_ { 

    public static volatile SingularAttribute<PuppetParamValue, Long> id;
    public static volatile SingularAttribute<PuppetParamValue, String> valor;
    public static volatile SingularAttribute<PuppetParamValue, String> name;
    public static volatile SingularAttribute<PuppetParamValue, PuppetModuleUsage> puppetModuleUsage;

}