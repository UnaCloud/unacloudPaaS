package unacloud.paas.data.managers;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import unacloud.paas.back.database.DatabaseConnection;
import unacloud.paas.data.entities.MainCommand;
import unacloud.paas.data.entities.Platform;
import unacloud.paas.data.entities.PlatformRole;
import unacloud.paas.data.entities.PuppetModuleParamValue;
import unacloud.paas.data.entities.PuppetModuleUsage;
import unacloud.paas.data.entities.enums.MultiplicityType;
import unacloud.paas.data.entities.enums.ResourceType;
public class PlatformManager{
   public static Platform getFullPlatform(int id){
      Platform ret=null;
      try(Connection con=DatabaseConnection.getConnection(); Statement st=con.createStatement();){
         try(ResultSet rs=st.executeQuery("select `name`,`imagename`,`waiterClass` from `unacloudpaas`.`platform` where `idplatform`="+id+";");){
            if(rs.next()){
               ret=new Platform(rs.getString(1), id, rs.getString(2),rs.getString(3));
            }
         }
         if(ret!=null){
            try(ResultSet rs=st.executeQuery("SELECT `role`.`name`,`role`.`multiplicity`,`role`.`templateId`,`role`.`puppetModuleUsage_id`,`puppetModuleUsage`.`puppetmodule_name`,`role`.`mountPlatformFolder` FROM `unacloudpaas`.`role`, `unacloudpaas`.`puppetModuleUsage` where `puppetModuleUsage`.`id`=`role`.`puppetModuleUsage_id` and `role`.`platform_idplatform` = "+id+";"); PreparedStatement ps=con.prepareStatement("SELECT `puppetparamvalue`.`name`,`puppetparamvalue`.`valor` FROM `unacloudpaas`.`puppetparamvalue` where `puppetparamvalue`.`puppetModuleUsage_id`=?;");){
               while(rs.next()){
                  PlatformRole role=new PlatformRole(rs.getString(1), MultiplicityType.getFromId(rs.getString(2).charAt(0)), rs.getInt(3), rs.getBoolean(6));
                  PuppetModuleUsage pmu=new PuppetModuleUsage(rs.getInt(4), PuppetModuleManager.getPuppetModule(rs.getString(5)));
                  ps.setInt(1, pmu.getId());
                  try(ResultSet rsPs=ps.executeQuery();){
                     while(rsPs.next())pmu.getParamValues().add(new PuppetModuleParamValue(rsPs.getString(1), rsPs.getString(2)));
                  }
                  role.setPuppetModule(pmu);
                  ret.getRoles().add(role);
               }
            }
            try(ResultSet rs=st.executeQuery("SELECT `shareSSHKeys`.`role_src`,`shareSSHKeys`.`role_dest` FROM `unacloudpaas`.`shareSSHKeys` where `shareSSHKeys`.`platform_idplatform`="+id+";");){
               while(rs.next())ret.getSshSharedKeys().add(new String[]{rs.getString(1), rs.getString(2)});
            }
            try(ResultSet rs=st.executeQuery("SELECT `MainCommand`.`role_name`,`MainCommand`.`command`,`MainCommand`.`runningUser`,`MainCommand`.`multiplicity`,`MainCommand`.`resourceType_id` FROM `MainCommand` where `MainCommand`.`idplatform`="+id+";");){
               if(rs.next()){
                  MainCommand mainCommand=new MainCommand(rs.getString(2), rs.getString(1),rs.getString(3),ResourceType.getFromId(rs.getInt(5)), MultiplicityType.getFromId(rs.getString(4).charAt(0)));
                  ret.setMainCommand(mainCommand);
               }
            }
         }
      }catch(SQLException ex){
         Logger.getLogger(PlatformManager.class.getName()).log(Level.SEVERE, null, ex);
      }
      return ret;
   }
   public static List<Platform> getList(){
      ArrayList<Platform> ret=new ArrayList<>();
      try(Connection con=DatabaseConnection.getConnection(); Statement st=con.createStatement(); ResultSet rs=st.executeQuery("select `idplatform`,`name`,`imagename`,`waiterClass` from `unacloudpaas`.`platform`;")){
         while(rs.next())ret.add(new Platform(rs.getString(2), rs.getInt(1), rs.getString(3),rs.getString(4)));
      }catch(SQLException ex){
         Logger.getLogger(PlatformManager.class.getName()).log(Level.SEVERE, null, ex);
      }
      return ret;
   }
   public static PlatformRole getRole(String roleName,int platformId,Connection con){
      PlatformRole role=new PlatformRole();
      role.setRoleName(roleName);
      return role;
   }
}
