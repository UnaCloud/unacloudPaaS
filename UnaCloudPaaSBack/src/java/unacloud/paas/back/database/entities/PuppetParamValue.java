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
    @UniqueConstraint(columnNames = {"puppetModuleUsage_id", "name"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Puppetparamvalue.findAll", query = "SELECT p FROM PuppetParamValue p"),
    @NamedQuery(name = "Puppetparamvalue.findByName", query = "SELECT p FROM PuppetParamValue p WHERE p.name = :name"),
    @NamedQuery(name = "Puppetparamvalue.findByValor", query = "SELECT p FROM PuppetParamValue p WHERE p.valor = :valor"),
    @NamedQuery(name = "Puppetparamvalue.findById", query = "SELECT p FROM PuppetParamValue p WHERE p.id = :id")})
public class PuppetParamValue implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String name;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String valor;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long id;
    
    @JoinColumn(name = "puppetModuleUsage_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private PuppetModuleUsage puppetModuleUsage;

    public PuppetParamValue() {
    }

    public PuppetParamValue(Long id) {
        this.id = id;
    }

    public PuppetParamValue(Long id, String name, String valor) {
        this.id = id;
        this.name = name;
        this.valor = valor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PuppetModuleUsage getPuppetModuleUsage() {
        return puppetModuleUsage;
    }

    public void setPuppetModuleUsage(PuppetModuleUsage puppetModuleUsageid) {
        this.puppetModuleUsage = puppetModuleUsageid;
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
        if (!(object instanceof PuppetParamValue)) {
            return false;
        }
        PuppetParamValue other = (PuppetParamValue) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "unacloud.paas.back.database.entities.Puppetparamvalue[ id=" + id + " ]";
    }
    
}
