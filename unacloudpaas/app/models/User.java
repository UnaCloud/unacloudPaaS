package models;

import play.data.format.*;
import play.data.validation.*;
import play.db.ebean.*;

import javax.persistence.*;
import java.util.List;

/**
 * Created by ga.sotelo69 on 25/04/2014.
 */
@Entity
public class User extends Model{
    @Id
    public long userId;

    @Column(unique = true)
    public String username;
    public String firstname;
    public String lastname;
    public String password;
    public String hash;
    public Boolean activated;
    public String email;
    public String description;
    @ManyToMany
    public List<Grupo> grupos;
    @OneToMany(mappedBy = "user")
    public List<PlatformExecution> platformExecutions;
    @OneToMany(mappedBy = "user")
    public List<UserFile> userFiles;

    public String getUsername() {
        return username;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPassword() {
        return password;
    }

    public String getHash() {
        return hash;
    }

    public Boolean getActivated() {
        return activated;
    }

    public String getEmail() {
        return email;
    }

    public String getDescription() {
        return description;
    }

    public List<Grupo> getGrupos() {
        return grupos;
    }

    public List<PlatformExecution> getPlatformExecutions() {
        return platformExecutions;
    }

    public List<UserFile> getUserFiles() {
        return userFiles;
    }

    public static Finder<String,User> find = new Finder<String,User>(String.class, User.class);
}
