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
import javax.validation.constraints.Size;
import unacloud.paas.back.database.enums.MultiplicityType;
import unacloud.paas.back.database.enums.ResourceType;

/**
 *
 * @author G
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Command.findAll", query = "SELECT c FROM Command c"),
    @NamedQuery(name = "Command.findById", query = "SELECT c FROM Command c WHERE c.id = :id"),
    @NamedQuery(name = "Command.findByCommand", query = "SELECT c FROM Command c WHERE c.command = :command"),
    @NamedQuery(name = "Command.findByMultiplicity", query = "SELECT c FROM Command c WHERE c.multiplicity = :multiplicity"),
    @NamedQuery(name = "Command.findByRunningUser", query = "SELECT c FROM Command c WHERE c.runningUser = :runningUser")})
public class Command implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long id;
    
    @Size(max = 150)
    @Column(length = 150)
    private String command;
    
    private MultiplicityType multiplicity;

    @Size(max = 45)
    @Column(length = 45)
    private String runningUser;
    
    @JoinColumn(name = "resourceType_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private ResourceType type;
    
    public Command() {
    }

    public Command(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public MultiplicityType getMultiplicity() {
        return multiplicity;
    }

    public void setMultiplicity(MultiplicityType multiplicity) {
        this.multiplicity = multiplicity;
    }

    public String getRunningUser() {
        return runningUser;
    }

    public void setRunningUser(String runningUser) {
        this.runningUser = runningUser;
    }

    public ResourceType getType() {
        return type;
    }

    public void setType(ResourceType type) {
        this.type = type;
    }
}
