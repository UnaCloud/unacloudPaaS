package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


/**
 * Created by ga.sotelo69 on 25/04/2014.
 */
@Entity()
public class UserFile extends Model{
    @Id
    public Long id;
    public String name;
    public String path;

    @ManyToOne
    public User user;

    public UserFile() {}

    public UserFile(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public User getUser() {
        return user;
    }

    public static Finder<Long,UserFile> find = new Finder<Long,UserFile>(Long.class, UserFile.class);
}
