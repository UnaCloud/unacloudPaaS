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
import javax.xml.bind.annotation.XmlRootElement;
import unacloud.paas.back.database.enums.ExecutionState;

/**
 *
 * @author G
 */
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"idNode", "processId"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CommandWait.findAll", query = "SELECT c FROM CommandWait c"),
    @NamedQuery(name = "CommandWait.findByProcessId", query = "SELECT c FROM CommandWait c WHERE c.processId = :processId"),
    @NamedQuery(name = "CommandWait.findById", query = "SELECT c FROM CommandWait c WHERE c.id = :id")})
public class CommandWait implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private long processId;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long id;
    
    private ExecutionState status;
    
    @JoinColumn(name = "idNode", referencedColumnName = "idNode", nullable = false)
    @ManyToOne(optional = false)
    private Node node;

    public CommandWait() {
    }

    public CommandWait(Long id) {
        this.id = id;
    }

    public CommandWait(Long id, int processId) {
        this.id = id;
        this.processId = processId;
    }

    public long getProcessId() {
        return processId;
    }

    public void setProcessId(long processId) {
        this.processId = processId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ExecutionState getStatus() {
        return status;
    }

    public void setStatus(ExecutionState status) {
        this.status = status;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
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
        if (!(object instanceof CommandWait)) {
            return false;
        }
        CommandWait other = (CommandWait) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "unacloud.paas.back.database.entities.CommandWait[ id=" + id + " ]";
    }
    
}
