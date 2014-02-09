package unacloud.paas.back.database.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import unacloud.paas.back.database.entities.Puppetmodule;
import unacloud.paas.back.database.entities.Puppetparamvalue;
import unacloud.paas.back.database.entities.Rol;
import unacloud.paas.back.database.entities.RolExecution;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-02-08T18:21:52")
@StaticMetamodel(PuppetModuleUsage.class)
public class PuppetModuleUsage_ { 

    public static volatile SingularAttribute<PuppetModuleUsage, Integer> id;
    public static volatile CollectionAttribute<PuppetModuleUsage, Puppetparamvalue> puppetparamvalueCollection;
    public static volatile CollectionAttribute<PuppetModuleUsage, Rol> rolCollection;
    public static volatile SingularAttribute<PuppetModuleUsage, Puppetmodule> puppetmoduleName;
    public static volatile CollectionAttribute<PuppetModuleUsage, RolExecution> rolExecutionCollection;

}