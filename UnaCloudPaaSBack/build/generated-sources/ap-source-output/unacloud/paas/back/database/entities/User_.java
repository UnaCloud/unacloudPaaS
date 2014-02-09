package unacloud.paas.back.database.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import unacloud.paas.back.database.entities.Grupo;
import unacloud.paas.back.database.entities.PlatformExecution;
import unacloud.paas.back.database.entities.UserFile;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-02-08T18:21:52")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile CollectionAttribute<User, PlatformExecution> platformExecutionCollection;
    public static volatile SingularAttribute<User, String> username;
    public static volatile SingularAttribute<User, String> hash;
    public static volatile SingularAttribute<User, String> email;
    public static volatile SingularAttribute<User, String> description;
    public static volatile SingularAttribute<User, Boolean> activated;
    public static volatile CollectionAttribute<User, Grupo> grupoCollection;
    public static volatile SingularAttribute<User, String> lastname;
    public static volatile SingularAttribute<User, String> firstname;
    public static volatile SingularAttribute<User, String> password;
    public static volatile CollectionAttribute<User, UserFile> userFileCollection;

}