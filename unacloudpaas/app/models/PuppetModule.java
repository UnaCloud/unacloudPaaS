package models;

import play.api.libs.json.JsObject;
import play.api.libs.json.Json;
import play.db.ebean.Model;
import scala.Tuple2;
import scala.collection.Seq;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class PuppetModule extends Model {
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

    public static Model.Finder<Long,PuppetModule> find = new Model.Finder<Long,PuppetModule>(Long.class, PuppetModule.class);

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
