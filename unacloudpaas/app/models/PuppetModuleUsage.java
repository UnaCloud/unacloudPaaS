package models;

import play.db.ebean.Model;
import unacloud.paas.back.dsl.Parser;
import unacloud.paas.back.puppet.PuppetModuleInstance;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Entity
public class PuppetModuleUsage  extends Model {
    @Id
    public Integer id;

    @ManyToOne
    public PuppetModule puppetModule;

    @OneToMany(cascade = CascadeType.PERSIST)
    public List<PuppetParamValue> puppetParamValue = new ArrayList<>();

    public PuppetModuleUsage() {
    }

    public PuppetModuleUsage(PuppetModule puppetModule) {
        this.puppetModule = puppetModule;
    }

    public Integer getId() {
        return id;
    }

    public PuppetModule getPuppetModule() {
        return puppetModule;
    }

    public List<PuppetParamValue> getPuppetParamValue() {
        return puppetParamValue;
    }

    public PuppetModuleInstance getpuppetModuleInstance(RolExecution role, PlatformExecution platform){
        PuppetModuleInstance pmi=new PuppetModuleInstance(getPuppetModule().getName());
        final Map<String, String> puppetValues=new TreeMap<>();
        if(getPuppetModule().getPuppetParams()!=null)for(PuppetParam pmp : getPuppetModule().getPuppetParams()){
            puppetValues.put(pmp.getName(), Parser.parseFromEnvironment(pmp.getDefaultValue(), role, platform));
        }
        if(getPuppetParamValue()!=null)for(PuppetParamValue pmp : getPuppetParamValue()){
            if(puppetValues.containsKey(pmp.getName())&&isValidPuppetValue(pmp.getValor())){
                puppetValues.put(pmp.getName(),Parser.parseFromEnvironment(pmp.getValor(),role,platform));
            }
        }
        for(Map.Entry<String, String> ent : puppetValues.entrySet()){
            if(ent.getKey()!=null&&!ent.getKey().trim().isEmpty()&&isValidPuppetValue(ent.getValue())){
                pmi.addValue(ent.getKey(), ent.getValue());
            }
        }
        return pmi;
    }
    private boolean isValidPuppetValue(String h){
        return h!=null&&!h.trim().isEmpty()&&!h.equalsIgnoreCase("insert");
    }
}
