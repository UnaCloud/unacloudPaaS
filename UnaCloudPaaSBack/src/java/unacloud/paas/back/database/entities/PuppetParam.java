/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unacloud.paas.back.database.entities;

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

/**
 *
 * @author G
 */
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"name", "puppetmodule_name"})})
@NamedQueries({
    @NamedQuery(name = "Puppetparam.findAll", query = "SELECT p FROM Puppetparam p"),
    @NamedQuery(name = "Puppetparam.findByName", query = "SELECT p FROM Puppetparam p WHERE p.name = :name"),
    @NamedQuery(name = "Puppetparam.findByDefaultValue", query = "SELECT p FROM Puppetparam p WHERE p.defaultValue = :defaultValue"),
    @NamedQuery(name = "Puppetparam.findById", query = "SELECT p FROM Puppetparam p WHERE p.id = :id")})
public class PuppetParam implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String name;
    @Size(max = 45)
    @Column(length = 45)
    private String defaultValue;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long id;
    @JoinColumn(name = "puppetmodule_name", referencedColumnName = "name", nullable = false)
    @ManyToOne(optional = false)
    private PuppetModule puppetmoduleName;

    public PuppetParam() {
    }

    public PuppetParam(Long id) {
        this.id = id;
    }

    public PuppetParam(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PuppetModule getPuppetmoduleName() {
        return puppetmoduleName;
    }

    public void setPuppetmoduleName(PuppetModule puppetmoduleName) {
        this.puppetmoduleName = puppetmoduleName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PuppetParam)) {
            return false;
        }
        PuppetParam other = (PuppetParam) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "unacloud.paas.back.database.entities.Puppetparam[ id=" + id + " ]";
    }
    
}
