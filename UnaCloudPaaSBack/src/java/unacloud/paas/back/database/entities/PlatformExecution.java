/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unacloud.paas.back.database.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import unacloud.paas.back.database.enums.ExecutionState;
import unacloudws.requests.VirtualClusterRequest;
import unacloudws.requests.VirtualImageRequest;

/**
 *
 * @author G
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlatformExecution.findAll", query = "SELECT p FROM PlatformExecution p"),
    @NamedQuery(name = "PlatformExecution.findById", query = "SELECT p FROM PlatformExecution p WHERE p.id = :id"),
    @NamedQuery(name = "PlatformExecution.findByRunName", query = "SELECT p FROM PlatformExecution p WHERE p.runName = :runName"),
    @NamedQuery(name = "PlatformExecution.findByStartTime", query = "SELECT p FROM PlatformExecution p WHERE p.startTime = :startTime"),
    @NamedQuery(name = "PlatformExecution.findByUsernameAndState", query = "SELECT p FROM PlatformExecution p WHERE p.user = :username AND p.status = :state"),
    @NamedQuery(name = "PlatformExecution.findByEternal", query = "SELECT p FROM PlatformExecution p WHERE p.eternal = TRUE AND p.status = :state")})
public class PlatformExecution implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long id;
    @Size(max = 45)
    @Column(length = 45)
    private String runName;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    
    private Boolean eternal;
    
    private ExecutionState status;
    
    @JoinColumn(name = "user_username", referencedColumnName = "username", nullable = false)
    @ManyToOne(optional = false)
    private User user;
    
    @JoinColumn(name = "platform_idplatform", referencedColumnName = "idplatform", nullable = false)
    @ManyToOne(optional = false)
    private Platform platform;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "platformExecutionid")
    private List<RolExecution> rolExecution;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "platformExecutionid")
    private List<ExecutionLog> executionLog;

    public PlatformExecution() {
    }

    public PlatformExecution(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRunName() {
        return runName;
    }

    public void setRunName(String runName) {
        this.runName = runName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Boolean getEternal() {
        return eternal;
    }

    public void setEternal(Boolean eternal) {
        this.eternal = eternal;
    }

    public ExecutionState getStatus() {
        return status;
    }

    public void setStatus(ExecutionState status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    @XmlTransient
    public List<RolExecution> getRolExecution() {
        return rolExecution;
    }

    public void setRolExecution(List<RolExecution> rolExecution) {
        this.rolExecution = rolExecution;
    }

    @XmlTransient
    public List<ExecutionLog> getExecutionLog() {
        return executionLog;
    }

    public void setExecutionLog(List<ExecutionLog> executionLog) {
        this.executionLog = executionLog;
    }

    @Transient
    public VirtualClusterRequest generateClusterRequest(){
        VirtualImageRequest[] images=new VirtualImageRequest[getPlatform().getRol().size()];
        int e=0;
        for(RolExecution rolExecution:getRolExecution()){
            images[e++]=rolExecution.generateVirtualMachineRequest();
        }
        VirtualClusterRequest ret=new VirtualClusterRequest(getPlatform().getClusterId(),1024,images);
        return ret;
    }
    
}
