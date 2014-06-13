package models;

import play.db.ebean.Model;

import javax.persistence.*;

/**
 * Created by ga.sotelo69 on 25/04/2014.
 */
@Entity
public class SSHSharedKey extends Model{
    @Id
    public Long id;

    @ManyToOne
    public Rol sourceRole;
    @ManyToOne
    public Rol targetRole;

    public SSHSharedKey() {
    }

    public SSHSharedKey(Rol sourceRole, Rol targetRole) {
        this.sourceRole = sourceRole;
        this.targetRole = targetRole;
    }

    public Long getId() {
        return id;
    }

    public Rol getSourceRole() {
        return sourceRole;
    }

    public Rol getTargetRole() {
        return targetRole;
    }
}
