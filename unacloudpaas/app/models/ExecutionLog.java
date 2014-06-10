package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.nio.charset.Charset;
import java.util.Date;

@Entity
public class ExecutionLog  extends Model {
    @Id
    public long id;
    public Long idNodeLog;
    public long platformExecutionId;

    @Temporal(TemporalType.TIMESTAMP)
    public Date time;
    public String component;
    @Column(columnDefinition = "text")
    public String message;

    @Transient
    public transient StringBuilder messageBuilder;

    public ExecutionLog(long platformExecutionId,Long idNodeLog, String component, String message) {
        this.platformExecutionId=platformExecutionId;
        this.idNodeLog = idNodeLog;
        this.component = component;
        this.message = message;
    }
    public ExecutionLog(long platformExecutionId,Long idNodeLog, String component) {
        this.platformExecutionId=platformExecutionId;
        this.idNodeLog = idNodeLog;
        this.component = component;
        messageBuilder=new StringBuilder();
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

    public void doFinal(){
        time=new Date();
        if(messageBuilder!=null){
            message=messageBuilder.toString();
            messageBuilder=null;
        }
    }

    public void appendLine(String line){
        messageBuilder.append(line);
        messageBuilder.append("\n");
    }
    public void appendChar(char c){
        messageBuilder.append(c);
    }

    public static Finder<Long,ExecutionLog> find = new Finder<Long,ExecutionLog>(Long.class, ExecutionLog.class);
}
