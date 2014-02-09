/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unacloud.paas.back.database.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author G
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Puppetmodule.findAll", query = "SELECT p FROM PuppetModule p"),
    @NamedQuery(name = "Puppetmodule.findByName", query = "SELECT p FROM PuppetModule p WHERE p.name = :name"),
    @NamedQuery(name = "Puppetmodule.findByNameLike", query = "SELECT p FROM PuppetModule p WHERE p.name like :name")})
public class PuppetModule implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String name;
    
    @Lob
    @Size(max = 65535)
    @Column(length = 65535)
    private String description;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "puppetmoduleName")
    private List<PuppetModuleUsage> puppetModuleUsage;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "puppetmoduleName")
    private List<PuppetParam> puppetParams;

    public PuppetModule() {
    }

    public PuppetModule(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public List<PuppetModuleUsage> getPuppetModuleUsage() {
        return puppetModuleUsage;
    }

    public void setPuppetModuleUsage(List<PuppetModuleUsage> puppetModuleUsage) {
        this.puppetModuleUsage = puppetModuleUsage;
    }
    
    @XmlTransient
    public List<PuppetParam> getPuppetParams() {
        return puppetParams;
    }

    public void setPuppetParams(List<PuppetParam> puppetParams) {
        this.puppetParams = puppetParams;
    }

    @Override
    public String toString() {
        return "unacloud.paas.back.database.entities.Puppetmodule[ name=" + name + " ]";
    }
    
}
