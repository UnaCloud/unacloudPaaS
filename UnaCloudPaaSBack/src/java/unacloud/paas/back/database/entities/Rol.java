/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unacloud.paas.back.database.entities;

import java.beans.Transient;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import unacloud.paas.back.database.enums.MultiplicityType;

/**
 *
 * @author G
 */
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"platform_idplatform", "name"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rol.findAll", query = "SELECT r FROM Rol r"),
    @NamedQuery(name = "Rol.findByName", query = "SELECT r FROM Rol r WHERE r.name = :name"),
    @NamedQuery(name = "Rol.findById", query = "SELECT r FROM Rol r WHERE r.id = :id")})
public class Rol implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String name;
    
    private MultiplicityType multiplicity;
    
    private Integer imageId;
    
    private Boolean mountPlatformFolder;
    
    @JoinColumn(name = "puppetModuleUsage_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private PuppetModuleUsage puppetModule;
    
    @JoinColumn(name = "platform_idplatform", referencedColumnName = "idplatform", nullable = false)
    @ManyToOne(optional = false)
    private Platform platform;

    public Rol() {
    }

    public Rol(Long id) {
        this.id = id;
    }

    public Rol(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MultiplicityType getMultiplicity() {
        return multiplicity;
    }

    public void setMultiplicity(MultiplicityType multiplicity) {
        this.multiplicity = multiplicity;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public Boolean getMountPlatformFolder() {
        return mountPlatformFolder;
    }

    public void setMountPlatformFolder(Boolean mountPlatformFolder) {
        this.mountPlatformFolder = mountPlatformFolder;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PuppetModuleUsage getPuppetModule() {
        return puppetModule;
    }

    public void setPuppetModule(PuppetModuleUsage puppetModule) {
        this.puppetModule = puppetModule;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }
}
