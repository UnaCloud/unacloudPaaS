package models;

import play.db.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.nio.charset.Charset;
import java.util.Date;

@Entity
public class ExecutionLog  extends Model {
    @Id
    public long id;
    public Long idNodeLog;
    public long platformExecutionId;

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
