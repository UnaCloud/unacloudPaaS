/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unacloud.paas.back.database.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import unacloud.paas.back.database.enums.MultiplicityType;
import unacloud.paas.back.database.enums.ResourceType;

/**
 *
 * @author G
 */
@Entity
public class MainCommand implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String command;
    
    private MultiplicityType multiplicity;
    
    private ResourceType resourceType;
    
    private String runningUser;
    
    private long roleId;    
    
    public Long getId() {
        return id;
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

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public String getRunningUser() {
        return runningUser;
    }

    public void setRunningUser(String runningUser) {
        this.runningUser = runningUser;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }
}
