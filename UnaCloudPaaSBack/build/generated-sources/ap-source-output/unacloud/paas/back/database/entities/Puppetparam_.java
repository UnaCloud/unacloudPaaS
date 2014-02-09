package unacloud.paas.back.database.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import unacloud.paas.back.database.entities.Puppetmodule;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-02-08T18:21:52")
@StaticMetamodel(Puppetparam.class)
public class Puppetparam_ { 

    public static volatile SingularAttribute<Puppetparam, Long> id;
    public static volatile SingularAttribute<Puppetparam, String> name;
    public static volatile SingularAttribute<Puppetparam, Puppetmodule> puppetmoduleName;
    public static volatile SingularAttribute<Puppetparam, String> defaultValue;

}