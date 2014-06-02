package models;

import models.enums.MultiplicityType;
import models.enums.ResourceType;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class MainCommand  extends Model {
    @Id
    public Long id;
    public String command;
    public MultiplicityType multiplicity;
    public ResourceType resourceType;
    public String runningUser;
    public long roleId;

    /*public MainCommand() {
    }*/

    public MainCommand(String command, MultiplicityType multiplicity, ResourceType resourceType, String runningUser, long roleId) {
        this.command = command;
        this.multiplicity = multiplicity;
        this.resourceType = resourceType;
        this.runningUser = runningUser;
        this.roleId = roleId;
    }

    public Long getId() {
        return id;
    }

    public MultiplicityType getMultiplicity() {
        return multiplicity;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public String getRunningUser() {
        return runningUser;
    }

    public long getRoleId() {
        return roleId;
    }

    public String getCommand() {
        return command;
    }
}
