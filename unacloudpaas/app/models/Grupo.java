package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by ga.sotelo69 on 25/04/2014.
 */
@Entity
public class Grupo  extends Model {
    @Id
    public String groupname;

    public String getGroupname() {
        return groupname;
    }
}
