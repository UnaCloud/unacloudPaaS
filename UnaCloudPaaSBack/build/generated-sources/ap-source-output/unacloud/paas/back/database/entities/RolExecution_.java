package unacloud.paas.back.database.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import unacloud.paas.back.database.entities.Command;
import unacloud.paas.back.database.entities.Node;
import unacloud.paas.back.database.entities.PlatformExecution;
import unacloud.paas.back.database.entities.PuppetModuleUsage;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-02-08T18:21:52")
@StaticMetamodel(RolExecution.class)
public class RolExecution_ { 

    public static volatile SingularAttribute<RolExecution, Integer> templateId;
    public static volatile SingularAttribute<RolExecution, Long> id;
    public static volatile CollectionAttribute<RolExecution, Node> nodeCollection;
    public static volatile SingularAttribute<RolExecution, Integer> rAMperCore;
    public static volatile CollectionAttribute<RolExecution, Command> commandCollection;
    public static volatile SingularAttribute<RolExecution, String> name;
    public static volatile CollectionAttribute<RolExecution, PuppetModuleUsage> puppetModuleUsageCollection;
    public static volatile SingularAttribute<RolExecution, String> puppetModuleName;
    public static volatile SingularAttribute<RolExecution, Integer> tamano;
    public static volatile SingularAttribute<RolExecution, Integer> cores;
    public static volatile SingularAttribute<RolExecution, PlatformExecution> platformExecutionid;
    public static volatile SingularAttribute<RolExecution, Integer> coresPerVM;

}