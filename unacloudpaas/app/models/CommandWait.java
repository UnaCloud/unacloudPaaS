package models;

import models.enums.ExecutionState;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by ga.sotelo69 on 25/04/2014.
 */
@Entity
public class CommandWait  extends Model {
    @Id
    public Long id;
    public long processId;
    public ExecutionState status;
    @ManyToOne
    public Node node;

    public Long getId() {
        return id;
    }

    public long getProcessId() {
        return processId;
    }

    public ExecutionState getStatus() {
        return status;
    }

    public Node getNode() {
        return node;
    }
}
