package models;

import play.db.ebean.Model;
import unacloudws.requests.VirtualImageRequest;

import javax.persistence.*;
import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

@Entity
public class RolExecution extends Model{
    @Id
    public Long id;
    @ManyToOne
    public Rol rol;
    public Integer coresPerVM;
    public Integer ramPerCore;
    public Integer size;

    @ManyToMany(cascade = CascadeType.PERSIST)
    public List<PuppetModuleUsage> puppetModuleUsage=new ArrayList<>();

    @OneToMany(mappedBy = "rolExecution",cascade =CascadeType.PERSIST)
    public List<Node> nodes=new ArrayList<>();
    @ManyToOne
    public PlatformExecution platformExecution;
    @OneToMany(cascade = CascadeType.ALL)
    public List<Command> commands=new ArrayList<>();

    public Long getId() {
        return id;
    }

    public Rol getRol() {
        return rol;
    }

    public int getCoresPerVM() {
        return coresPerVM;
    }

    public int getRamPerCore() {
        return ramPerCore;
    }

    public int getSize() {
        return size;
    }

    public List<PuppetModuleUsage> getPuppetModuleUsage() {
        return puppetModuleUsage;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public PlatformExecution getPlatformExecution() {
        return platformExecution;
    }

    public List<Command> getCommands() {
        return commands;
    }

    public int getCores(){
        return coresPerVM*size;
    }

    @Override
    public String toString() {
        return "RolExecution{" +
                "size=" + size +
                ", id=" + id +
                ", rol=" + rol +
                ", coresPerVM=" + coresPerVM +
                ", ramPerCore=" + ramPerCore +
                '}';
    }

    @Transient
    public VirtualImageRequest generateVirtualMachineRequest(){
        System.out.println(this);
        int size = (getCores() - 1) / getCoresPerVM() + 1;
        return new VirtualImageRequest(size,ramPerCore*coresPerVM,coresPerVM,rol.imageId,"test");
    }
}
