package unacloud.paas.back.database.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import unacloud.paas.back.database.entities.PuppetModule;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-02-09T20:37:18")
@StaticMetamodel(PuppetParam.class)
public class PuppetParam_ { 

    public static volatile SingularAttribute<PuppetParam, Long> id;
    public static volatile SingularAttribute<PuppetParam, PuppetModule> puppetModule;
    public static volatile SingularAttribute<PuppetParam, String> name;
    public static volatile SingularAttribute<PuppetParam, String> defaultValue;

}