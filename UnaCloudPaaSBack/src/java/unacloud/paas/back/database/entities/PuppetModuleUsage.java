/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unacloud.paas.back.database.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import unacloud.paas.back.dsl.Parser;
import unacloud.paas.back.puppet.PuppetModuleInstance;

/**
 *
 * @author G
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PuppetModuleUsage.findAll", query = "SELECT p FROM PuppetModuleUsage p"),
    @NamedQuery(name = "PuppetModuleUsage.findById", query = "SELECT p FROM PuppetModuleUsage p WHERE p.id = :id")})
public class PuppetModuleUsage implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    
    @JoinColumn(name = "puppetmodule_name", referencedColumnName = "name", nullable = false)
    @ManyToOne(optional = false)
    private PuppetModule puppetModule;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "puppetModuleUsage")
    private List<PuppetParamValue> puppetParamValue;
    
    public PuppetModuleUsage() {
    }

    public PuppetModuleUsage(PuppetModule puppetModule) {
        this.puppetModule = puppetModule;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PuppetModule getPuppetModule() {
        return puppetModule;
    }

    public void setPuppetModule(PuppetModule puppetModule) {
        this.puppetModule = puppetModule;
    }

    @XmlTransient
    public List<PuppetParamValue> getPuppetParamValue() {
        return puppetParamValue;
    }

    public void setPuppetParamValue(List<PuppetParamValue> puppetparamvalue) {
        this.puppetParamValue = puppetparamvalue;
    }
    public PuppetModuleInstance getpuppetModuleInstance(RolExecution role, PlatformExecution platform){
      PuppetModuleInstance pmi=new PuppetModuleInstance(getPuppetModule().getName());
      final Map<String, String> puppetValues=new TreeMap<>();
      if(getPuppetModule().getPuppetParams()!=null)for(PuppetParam pmp : getPuppetModule().getPuppetParams()){
         puppetValues.put(pmp.getName(), Parser.parseFromEnvironment(pmp.getDefaultValue(),role, platform));
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
