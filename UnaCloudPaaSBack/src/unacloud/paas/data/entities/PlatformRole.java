package unacloud.paas.data.entities;
import java.beans.Transient;
import java.io.Serializable;
import java.util.List;
import unacloud.paas.data.entities.enums.MultiplicityType;
public class PlatformRole implements Serializable{
   String roleName;
   MultiplicityType multiplicity;
   int templateId;
   PuppetModuleUsage puppetModule=null;
   public PlatformRole(){
   }
   public PlatformRole(String roleName, MultiplicityType multiplicity, int templateId, boolean usePlatformSharedFolder){
      this.roleName=roleName;
      this.multiplicity=multiplicity;
      this.templateId=templateId;
   }
   public String getRoleName(){
      return roleName;
   }
   public void setRoleName(String roleName){
      this.roleName=roleName;
   }
   public MultiplicityType getMultiplicity(){
      return multiplicity;
   }
   public void setMultiplicity(MultiplicityType multiplicity){
      this.multiplicity=multiplicity;
   }
   public int getTemplateId(){
      return templateId;
   }
   public void setTemplateId(int templateId){
      this.templateId=templateId;
   }
   public PuppetModuleUsage getPuppetModule(){
      return puppetModule;
   }
   public void setPuppetModule(PuppetModuleUsage puppetModule){
      this.puppetModule=puppetModule;
   }
   @Override
   public String toString(){
      return "PlatformRole{"+"multiplicity="+multiplicity+", templateId="+templateId+'}';
   }
   @Transient
   public String getMultiplicityString(){
      return (""+getMultiplicity()).toLowerCase();
   }
}
