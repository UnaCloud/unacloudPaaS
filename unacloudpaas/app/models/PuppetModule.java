package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class PuppetModule  extends Model {
    @Id
    public long id;
    //@Column(unique = true)
    public String name;
    public String description;

    @OneToMany(cascade = CascadeType.PERSIST)
    public List<PuppetParam> puppetParams=new ArrayList<>();

    public PuppetModule(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public PuppetModule() {
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<PuppetParam> getPuppetParams() {
        return puppetParams;
    }
}
