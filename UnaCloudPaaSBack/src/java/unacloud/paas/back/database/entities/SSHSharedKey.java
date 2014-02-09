/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unacloud.paas.back.database.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author G
 */
@Entity
public class SSHSharedKey implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @ManyToOne
    Platform platform;
    
    @OneToOne
    Rol sourceRole;
    
    @OneToOne
    Rol targetRole;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public Rol getSourceRole() {
        return sourceRole;
    }

    public void setSourceRole(Rol sourceRole) {
        this.sourceRole = sourceRole;
    }

    public Rol getTargetRole() {
        return targetRole;
    }

    public void setTargetRole(Rol targetRole) {
        this.targetRole = targetRole;
    }

}
