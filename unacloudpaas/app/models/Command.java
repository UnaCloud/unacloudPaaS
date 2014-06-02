package models;

import models.enums.MultiplicityType;
import models.enums.ResourceType;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by ga.sotelo69 on 25/04/2014.
 */
@Entity
public class Command  extends Model {
    @Id
    public Long id;
    public String command;
    public MultiplicityType multiplicity;
    public String runningUser;
    public ResourceType type;
    public boolean mainCommand;

    public Long getId() {
        return id;
    }

    public String getCommand() {
        return command;
    }

    public MultiplicityType getMultiplicity() {
        return multiplicity;
    }

    public String getRunningUser() {
        return runningUser;
    }

    public ResourceType getType() {
        return type;
    }

    public boolean isMainCommand() {
        return mainCommand;
    }

}
