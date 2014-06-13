package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Platform extends Model {
    @Id
    public Long id;
    public String name;
    public long clusterId;
    public String waiterClass;

    @OneToMany(mappedBy = "platform",cascade = CascadeType.PERSIST)
    public List<Rol> roles=new ArrayList<>();
    @OneToMany(mappedBy = "platform",cascade = CascadeType.PERSIST)
    public List<SSHSharedKey> sshSharedKeys=new ArrayList<>();

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    public MainCommand mainCommand;

    public Platform(long clusterId, String name, String waiterClass) {
        this.clusterId = clusterId;
        this.name = name;
        this.waiterClass = waiterClass;
    }

    public Platform() {}

    public Platform(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getClusterId() {
        return clusterId;
    }

    public String getWaiterClass() {
        return waiterClass;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public List<SSHSharedKey> getSshSharedKeys() {
        return sshSharedKeys;
    }

    public MainCommand getMainCommand() {
        return mainCommand;
    }

    public static Finder<Long,Platform> find = new Finder<Long,Platform>(Long.class, Platform.class);
}
