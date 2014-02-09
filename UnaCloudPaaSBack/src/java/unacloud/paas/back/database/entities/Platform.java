/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unacloud.paas.back.database.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author G
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Platform.findAll", query = "SELECT p FROM Platform p"),
    @NamedQuery(name = "Platform.findById", query = "SELECT p FROM Platform p WHERE p.id = :id"),
    @NamedQuery(name = "Platform.findByName", query = "SELECT p FROM Platform p WHERE p.name = :name"),
    @NamedQuery(name = "Platform.findByImagename", query = "SELECT p FROM Platform p WHERE p.imagename = :imagename"),
    @NamedQuery(name = "Platform.findByWaiterClass", query = "SELECT p FROM Platform p WHERE p.waiterClass = :waiterClass")})
public class Platform implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long id;
    
    @Size(max = 45)
    @Column(length = 45)
    private String name;
    @Size(max = 30)
    @Column(length = 30)
    private String imagename;
    
    private long clusterId;
    
    @Size(max = 45)
    @Column(length = 45)
    private String waiterClass;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "platformIdplatform")
    private List<Rol> rol;
    
    @OneToMany(mappedBy = "platform")
    private List<SSHSharedKey> sshSharedKeys;

    public Platform() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagename() {
        return imagename;
    }

    public void setImagename(String imagename) {
        this.imagename = imagename;
    }

    public String getWaiterClass() {
        return waiterClass;
    }

    public void setWaiterClass(String waiterClass) {
        this.waiterClass = waiterClass;
    }

    @XmlTransient
    public List<Rol> getRol() {
        return rol;
    }

    public void setRol(List<Rol> rol) {
        this.rol = rol;
    }

    @XmlTransient
    public List<SSHSharedKey> getSshSharedKeys() {
        return sshSharedKeys;
    }
    
    public void setSshSharedKeys(List<SSHSharedKey> sshSharedKeys) {
        this.sshSharedKeys = sshSharedKeys;
    }

    public long getClusterId() {
        return clusterId;
    }

    public void setClusterId(long clusterId) {
        this.clusterId = clusterId;
    }
    
}
