package models;

import models.enums.ExecutionState;
import play.db.ebean.Model;
import unacloudws.requests.VirtualClusterRequest;
import unacloudws.requests.VirtualImageRequest;

import javax.persistence.*;
import java.beans.Transient;
import java.util.Date;
import java.util.List;

/**
 * Created by ga.sotelo69 on 25/04/2014.
 */
@Entity
public class PlatformExecution  extends Model {
    @Id
    public Long id;
    public String runName;
    public String mainCommandArgs;
    public Date startTime;
    public Boolean eternal;
    public ExecutionState status;
    @ManyToOne
    public User user;
    @ManyToOne
    public Platform platform;
    @OneToMany(mappedBy = "platformExecution",cascade = CascadeType.PERSIST)
    public List<RolExecution> rolExecution;

    public Long getId() {
        return id;
    }

    public String getRunName() {
        return runName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Boolean getEternal() {
        return eternal;
    }

    public ExecutionState getStatus() {
        return status;
    }

    public User getUser() {
        return user;
    }

    public Platform getPlatform() {
        return platform;
    }

    public List<RolExecution> getRolExecution() {
        return rolExecution;
    }

    public static Finder<Long,PlatformExecution> find = new Finder<Long,PlatformExecution>(Long.class, PlatformExecution.class);

    @Transient
    public VirtualClusterRequest generateClusterRequest(){
        VirtualImageRequest[] images=new VirtualImageRequest[getPlatform().getRoles().size()];
        int e=0;
        for(RolExecution rolExecution:getRolExecution()){
            images[e++]=rolExecution.generateVirtualMachineRequest();
        }
        VirtualClusterRequest ret=new VirtualClusterRequest(getPlatform().getClusterId(),1024,images);
        return ret;
    }
}
