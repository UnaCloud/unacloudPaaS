package unacloud.paas.back.database.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import unacloud.paas.back.database.entities.User;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-02-08T18:21:52")
@StaticMetamodel(UserFile.class)
public class UserFile_ { 

    public static volatile SingularAttribute<UserFile, Long> id;
    public static volatile SingularAttribute<UserFile, String> name;
    public static volatile SingularAttribute<UserFile, String> path;
    public static volatile SingularAttribute<UserFile, User> userUsername;
    public static volatile SingularAttribute<UserFile, String> userFilecol;

}