package unacloud.paas.back.database.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import unacloud.paas.back.database.entities.Command;
import unacloud.paas.back.database.entities.Node;
import unacloud.paas.back.database.entities.PlatformExecution;
import unacloud.paas.back.database.entities.PuppetModuleUsage;
import unacloud.paas.back.database.entities.Rol;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-02-09T18:35:10")
@StaticMetamodel(RolExecution.class)
public class RolExecution_ { 

    public static volatile SingularAttribute<RolExecution, Long> id;
    public static volatile SingularAttribute<RolExecution, PlatformExecution> platformExecution;
    public static volatile ListAttribute<RolExecution, Node> nodes;
    public static volatile SingularAttribute<RolExecution, Integer> ramPerCore;
    public static volatile ListAttribute<RolExecution, Command> command;
    public static volatile SingularAttribute<RolExecution, Rol> rol;
    public static volatile SingularAttribute<RolExecution, String> puppetModuleName;
    public static volatile SingularAttribute<RolExecution, Integer> tamano;
    public static volatile SingularAttribute<RolExecution, Integer> cores;
    public static volatile ListAttribute<RolExecution, PuppetModuleUsage> puppetModuleUsage;
    public static volatile SingularAttribute<RolExecution, Integer> coresPerVM;

}