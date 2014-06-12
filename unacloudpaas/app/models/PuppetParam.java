package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by ga.sotelo69 on 25/04/2014.
 */
@Entity
public class PuppetParam  extends Model {
    @Id
    public long id;
    public String name;
    public String defaultValue;

    public PuppetParam() {
    }

    public PuppetParam(String name, String defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

}
