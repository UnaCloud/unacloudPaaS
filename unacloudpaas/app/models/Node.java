package models;

import play.db.ebean.Model;
import unacloud.paas.back.puppet.PuppetModuleInstance;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Node extends Model {
    @Id
    public Long id;
    public String ipAddress;
    public String hostname;
    public String iaasExecutionId;
    public int maxSecuentialFailCount;

    public List<PuppetModuleInstance> puppetModuleInstances=new ArrayList<>();
    @ManyToOne
    public RolExecution rolExecution;
    @OneToMany(mappedBy = "node")
    public List<CommandWait> waitingCommands=new ArrayList<>();

    public Long getId() {
        return id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getHostname() {
        return hostname;
    }

    public String getIaasExecutionId() {
        return iaasExecutionId;
    }

    public int getMaxSecuentialFailCount() {
        return maxSecuentialFailCount;
    }

    public RolExecution getRolExecution() {
        return rolExecution;
    }

    public List<CommandWait> getWaitingCommands() {
        return waitingCommands;
    }

    public List<PuppetModuleInstance> getPuppetModuleInstances() {
        return puppetModuleInstances;
    }

    public static Finder<Long,Node> find = new Finder<Long,Node>(Long.class, Node.class);
}
