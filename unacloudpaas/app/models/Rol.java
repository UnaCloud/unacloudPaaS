package models;

import models.enums.MultiplicityType;
import play.db.ebean.Model;

import javax.persistence.*;

/**
 * Created by ga.sotelo69 on 25/04/2014.
 */
@Entity
public class Rol extends Model {
    @Id
    public Long id;
    public String name;
    public MultiplicityType multiplicity;
    public Integer imageId;
    public Boolean mountPlatformFolder;

    @ManyToOne(cascade = CascadeType.ALL)
    public PuppetModuleUsage puppetModule;

    @ManyToOne
    public Platform platform;

    public Rol() {
    }

    public Rol(long id,Integer imageId, Boolean mountPlatformFolder, MultiplicityType multiplicity, String name) {
        this.id=id;
        this.imageId = imageId;
        this.mountPlatformFolder = mountPlatformFolder;
        this.multiplicity = multiplicity;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public MultiplicityType getMultiplicity() {
        return multiplicity;
    }

    public Integer getImageId() {
        return imageId;
    }

    public Boolean getMountPlatformFolder() {
        return mountPlatformFolder;
    }

    public PuppetModuleUsage getPuppetModule() {
        return puppetModule;
    }

    public Platform getPlatform() {
        return platform;
    }
}
