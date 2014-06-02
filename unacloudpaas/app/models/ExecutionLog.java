package models;

import play.db.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class ExecutionLog  extends Model {
    @Id
    public long id;
    public long idNodeLog;
    public Date time;
    public String component;
    @Column(columnDefinition = "text")
    public String message;
    public long platformExecutionId;
    public ExecutionLog(long idNodeLog, String component, String message) {
        this.idNodeLog = idNodeLog;
        this.component = component;
        this.message = message;
    }
    public ExecutionLog(){
    }
    public long getIdNodeLog() {
        return idNodeLog;
    }

    public Date getTime() {
        return time;
    }

    public String getComponent() {
        return component;
    }

    public String getMessage() {
        return message;
    }

    public long getPlatformExecutionId() {
        return platformExecutionId;
    }
}
