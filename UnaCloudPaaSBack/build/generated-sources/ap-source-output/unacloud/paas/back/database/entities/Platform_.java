package unacloud.paas.back.database.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import unacloud.paas.back.database.entities.MainCommand;
import unacloud.paas.back.database.entities.Rol;
import unacloud.paas.back.database.entities.SSHSharedKey;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-02-09T20:37:18")
@StaticMetamodel(Platform.class)
public class Platform_ { 

    public static volatile SingularAttribute<Platform, Long> id;
    public static volatile SingularAttribute<Platform, String> name;
    public static volatile SingularAttribute<Platform, String> waiterClass;
    public static volatile ListAttribute<Platform, Rol> rol;
    public static volatile SingularAttribute<Platform, Long> clusterId;
    public static volatile ListAttribute<Platform, SSHSharedKey> sshSharedKeys;
    public static volatile SingularAttribute<Platform, MainCommand> mainCommand;

}