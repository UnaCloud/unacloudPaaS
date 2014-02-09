/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unacloud.paas.back.database.entities;

import java.beans.Transient;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import unacloudws.requests.VirtualImageRequest;

/**
 *
 * @author G
 */
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"platformExecution_id", "name"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RolExecution.findAll", query = "SELECT r FROM RolExecution r"),
    @NamedQuery(name = "RolExecution.findByCores", query = "SELECT r FROM RolExecution r WHERE r.cores = :cores"),
    @NamedQuery(name = "RolExecution.findByTemplateId", query = "SELECT r FROM RolExecution r WHERE r.templateId = :templateId"),
    @NamedQuery(name = "RolExecution.findByPuppetModuleName", query = "SELECT r FROM RolExecution r WHERE r.puppetModuleName = :puppetModuleName"),
    @NamedQuery(name = "RolExecution.findByTamano", query = "SELECT r FROM RolExecution r WHERE r.tamano = :tamano"),
    @NamedQuery(name = "RolExecution.findById", query = "SELECT r FROM RolExecution r WHERE r.id = :id")})
public class RolExecution implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @ManyToOne
    private Rol rol;
    
    private Integer cores;
    @Size(max = 45)
    @Column(length = 45)
    private String puppetModuleName;
    private Integer coresPerVM;
    private Integer ramPerCore;
    private Integer tamano;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Long id;
    
    @JoinTable(name = "rolExecution_has_puppetModuleUsage", joinColumns = {
        @JoinColumn(name = "rolExecution_platformExecution_id", referencedColumnName = "platformExecution_id", nullable = false),
        @JoinColumn(name = "rolExecution_name", referencedColumnName = "name", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "puppetModuleUsage_id", referencedColumnName = "id", nullable = false)})
    @ManyToMany
    
    private List<PuppetModuleUsage> puppetModuleUsage;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rolExecution")
    
    private List<Node> nodes;
    @JoinColumn(name = "platformExecution_id", referencedColumnName = "id", nullable = false)
    
    @ManyToOne(optional = false)
    private PlatformExecution platformExecution;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rolExecution")
    private List<Command> command;

    public RolExecution() {
    }

    public RolExecution(Long id) {
        this.id = id;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Integer getCores() {
        return cores;
    }

    public void setCores(Integer cores) {
        this.cores = cores;
    }

    public String getPuppetModuleName() {
        return puppetModuleName;
    }

    public void setPuppetModuleName(String puppetModuleName) {
        this.puppetModuleName = puppetModuleName;
    }

    public Integer getCoresPerVM() {
        return coresPerVM;
    }

    public void setCoresPerVM(Integer coresPerVM) {
        this.coresPerVM = coresPerVM;
    }

    public Integer getRamPerCore() {
        return ramPerCore;
    }

    public void setRamPerCore(Integer ramPerCore) {
        this.ramPerCore = ramPerCore;
    }

    public Integer getTamano() {
        return tamano;
    }

    public void setTamano(Integer tamano) {
        this.tamano = tamano;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @XmlTransient
    public List<PuppetModuleUsage> getPuppetModuleUsage() {
        return puppetModuleUsage;
    }

    public void setPuppetModuleUsage(List<PuppetModuleUsage> puppetModuleUsage) {
        this.puppetModuleUsage = puppetModuleUsage;
    }

    @XmlTransient
    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public PlatformExecution getPlatformExecution() {
        return platformExecution;
    }

    public void setPlatformExecution(PlatformExecution platformExecution) {
        this.platformExecution = platformExecution;
    }

    @XmlTransient
    public List<Command> getCommand() {
        return command;
    }

    public void setCommand(List<Command> command) {
        this.command = command;
    }

    @Transient
    public VirtualImageRequest generateVirtualMachineRequest(){
        int size = (getCores() - 1) / getCoresPerVM() + 1;
        return new VirtualImageRequest(size,getRamPerCore()*getCores(),getCores(),getRol().getImageId());
    }
    
}
