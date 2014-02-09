/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unacloud.paas.back.database.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import unacloud.paas.back.puppet.PuppetModuleInstance;

/**
 *
 * @author G
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Node.findAll", query = "SELECT n FROM Node n"),
    @NamedQuery(name = "Node.findByIdNode", query = "SELECT n FROM Node n WHERE n.id = :id"),
    @NamedQuery(name = "Node.findByIpAddress", query = "SELECT n FROM Node n WHERE n.ipAddress = :ipAddress"),
    @NamedQuery(name = "Node.findByIaasExecutionId", query = "SELECT n FROM Node n WHERE n.iaasExecutionId = :iaasExecutionId"),
    @NamedQuery(name = "Node.findByHostname", query = "SELECT n FROM Node n WHERE n.hostname = :hostname")})
public class Node implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long id;
    @Size(max = 20)
    @Column(length = 20)
    private String ipAddress;
    
    @Size(max = 30)
    @Column(length = 30)
    private String hostname;
    
    @Size(max = 90)
    @Column(length = 90)
    private String iaasExecutionId;
    
    private Integer maxSecuentialFailCount;
    
    @Transient
    private List<PuppetModuleInstance> puppetModuleInstances=new ArrayList<>();
    
    @JoinColumns({
        @JoinColumn(name = "platformExecution_id", referencedColumnName = "platformExecution_id", nullable = false),
        @JoinColumn(name = "roleExecution_name", referencedColumnName = "name", nullable = false)})
    @ManyToOne(optional = false)
    private RolExecution rolExecution;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "node")
    private List<CommandWait> waitingCommands;

    public Node() {
    }

    public Long getId() {
        return id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getIaasExecutionId() {
        return iaasExecutionId;
    }

    public void setIaasExecutionId(String iaasExecutionId) {
        this.iaasExecutionId = iaasExecutionId;
    }

    public Integer getMaxSecuentialFailCount() {
        return maxSecuentialFailCount;
    }

    public void setMaxSecuentialFailCount(Integer maxSecuentialFailCount) {
        this.maxSecuentialFailCount = maxSecuentialFailCount;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public RolExecution getRolExecution() {
        return rolExecution;
    }

    public void setRolExecution(RolExecution rolExecution) {
        this.rolExecution = rolExecution;
    }

    @XmlTransient
    public List<CommandWait> getWaitingCommands() {
        return waitingCommands;
    }

    public void setWaitingCommands(List<CommandWait> waitingCommands) {
        this.waitingCommands = waitingCommands;
    }

    public void setPuppetModuleInstances(List<PuppetModuleInstance> puppetModuleInstances) {
        this.puppetModuleInstances = puppetModuleInstances;
    }

    @Transient
    public List<PuppetModuleInstance> getPuppetModuleInstances() {
        return puppetModuleInstances;
    }
}
