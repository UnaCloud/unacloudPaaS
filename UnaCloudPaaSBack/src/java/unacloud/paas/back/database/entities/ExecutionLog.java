/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unacloud.paas.back.database.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author G
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "ExecutionLog.findAll", query = "SELECT e FROM ExecutionLog e"),
    @NamedQuery(name = "ExecutionLog.findByIdnodeLog", query = "SELECT e FROM ExecutionLog e WHERE e.idnodeLog = :idnodeLog"),
    
    @NamedQuery(name = "ExecutionLog.findByComponent", query = "SELECT e FROM ExecutionLog e WHERE e.component = :component")})
public class ExecutionLog implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer idnodeLog;
    
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date tiempo;
    
    @Lob
    @Size(max = 16777215)
    @Column(length = 16777215)
    private String message;
    
    @Size(max = 45)
    @Column(length = 45)
    private String component;
    
    private long platformExecutionId;

    public ExecutionLog() {
    }

    public ExecutionLog(long platformExecutionId,String component, String message) {
        this.message = message;
        this.component = component;
        this.platformExecutionId = platformExecutionId;
    }

    public Integer getIdnodeLog() {
        return idnodeLog;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public Date getTiempo() {
        return tiempo;
    }

    public void setTiempo(Date tiempo) {
        this.tiempo = tiempo;
    }

    public long getPlatformExecutionId() {
        return platformExecutionId;
    }

    public void setPlatformExecutionId(long platformExecutionId) {
        this.platformExecutionId = platformExecutionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idnodeLog != null ? idnodeLog.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ExecutionLog)) {
            return false;
        }
        ExecutionLog other = (ExecutionLog) object;
        if ((this.idnodeLog == null && other.idnodeLog != null) || (this.idnodeLog != null && !this.idnodeLog.equals(other.idnodeLog))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "unacloud.paas.back.database.entities.ExecutionLog[ idnodeLog=" + idnodeLog + " ]";
    }
    
}
