package unacloud.paas.back.database.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import unacloud.paas.back.database.entities.Platform;
import unacloud.paas.back.database.entities.Rol;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-02-09T20:37:18")
@StaticMetamodel(SSHSharedKey.class)
public class SSHSharedKey_ { 

    public static volatile SingularAttribute<SSHSharedKey, Long> id;
    public static volatile SingularAttribute<SSHSharedKey, Platform> platform;
    public static volatile SingularAttribute<SSHSharedKey, Rol> sourceRole;
    public static volatile SingularAttribute<SSHSharedKey, Rol> targetRole;

}