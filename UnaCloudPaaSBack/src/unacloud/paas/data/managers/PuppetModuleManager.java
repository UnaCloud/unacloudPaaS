/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
import unacloud.paas.data.entities.PuppetModule;
import unacloud.paas.data.entities.PuppetModuleParam;
/**
 *
 * @author G
 */
public class PuppetModuleManager{
   public static void fillPuppetModule(PuppetModule module){
      try(Connection con=DatabaseConnection.getConnection(); PreparedStatement ps=con.prepareStatement("select `name`,`defaultValue` from `unacloudpaas`.`puppetparam` where `puppetmodule_name`=?;");){
         ps.setString(1, module.getName());
         try(ResultSet rs=ps.executeQuery()){
            while(rs.next()){
               module.setParameters(new ArrayList<PuppetModuleParam>());
               module.getParameters().add(new PuppetModuleParam(rs.getString(1), rs.getString(2)));
            }
         }
      }catch(SQLException ex){
         Logger.getLogger(PuppetModuleManager.class.getName()).log(Level.SEVERE, null, ex);
      }
   }
   public static boolean insertPuppetModule(PuppetModule module){
      try(Connection con=DatabaseConnection.getConnection(false); PreparedStatement ps=con.prepareStatement("INSERT INTO `unacloudpaas`.`puppetmodule`(`name`,`description`) VALUES (?,?);");){
         ps.setString(1, module.getName());
         ps.setString(2, module.getDescripcion());
         ps.executeUpdate();
         ps.close();
         if(module.getParameters()!=null){
            try(PreparedStatement pspp=con.prepareStatement("INSERT INTO `unacloudpaas`.`puppetparam`(`name`,`puppetmodule_name`,`defaultValue`) VALUES (?,?,?);");){
               for(PuppetModuleParam pmp : module.getParameters()){
                  if(pmp.getName()!=null&&!pmp.getName().trim().isEmpty()&&!pmp.getName().equals("Insert")){
                     ps.setString(1, pmp.getName());
                     ps.setString(2, module.getName());
                     ps.setString(3, pmp.getDefaultValue());
                     ps.executeUpdate();
                  }
               }
            }
         }
         con.commit();
         return true;
      }catch(SQLException ex){
         Logger.getLogger(PuppetModuleManager.class.getName()).log(Level.SEVERE, null, ex);
      }
      return false;
   }
   public static boolean updatePuppetModule(PuppetModule module){
      try(Connection con=DatabaseConnection.getConnection(false);){
         try(PreparedStatement ps=con.prepareStatement("delete from `unacloudpaas`.`puppetparam` where `puppetmodule_name`=?;");){
            ps.setString(1, module.getName());
            ps.executeUpdate();
         }
         try(PreparedStatement ps=con.prepareStatement("update `unacloudpaas`.`puppetmodule` set `description`=? where `name`=?;");){
            ps.setString(1, module.getDescripcion());
            ps.setString(2, module.getName());
            ps.executeUpdate();
         }
         if(module.getParameters()!=null){
            try(PreparedStatement ps=con.prepareStatement("INSERT INTO `unacloudpaas`.`puppetparam`(`name`,`puppetmodule_name`,`defaultValue`) VALUES (?,?,?);");){
               for(PuppetModuleParam pmp : module.getParameters()){
                  if(pmp.getName()!=null&&!pmp.getName().trim().isEmpty()&&!pmp.getName().equals("Insert")){
                     ps.setString(1, pmp.getName());
                     ps.setString(2, module.getName());
                     ps.setString(3, pmp.getDefaultValue());
                     ps.executeUpdate();
                  }
               }
            }
         }
         con.commit();
         return true;
      }catch(SQLException ex){
         Logger.getLogger(PuppetModuleManager.class.getName()).log(Level.SEVERE, null, ex);
      }
      return false;
   }
   public static List<PuppetModule> getPuppetModules(){
      List<PuppetModule> ret=new ArrayList<>();
      try(Connection con=DatabaseConnection.getConnection(); Statement st=con.createStatement(); ResultSet rs=st.executeQuery("select `name`,`description` from `unacloudpaas`.`puppetmodule`;")){
         while(rs.next())ret.add(new PuppetModule(rs.getString(1), rs.getString(2)));
      }catch(SQLException ex){
         Logger.getLogger(PuppetModuleManager.class.getName()).log(Level.SEVERE, null, ex);
      }
      return ret;
   }
   public static List<PuppetModule> getPuppetModules(String query){
      List<PuppetModule> ret=new ArrayList<>();
      try(Connection con=DatabaseConnection.getConnection(); PreparedStatement st=con.prepareStatement("select `name`,`description` from `unacloudpaas`.`puppetmodule` where `name` like ?;");){
         st.setString(1, "%"+query+"%");
         try(ResultSet rs=st.executeQuery()){
            while(rs.next())ret.add(new PuppetModule(rs.getString(1), rs.getString(2)));
         }
      }catch(SQLException ex){
         Logger.getLogger(PuppetModuleManager.class.getName()).log(Level.SEVERE, null, ex);
      }
      return ret;
   }
   public static PuppetModule getPuppetModule(String name){
      PuppetModule ret=null;
      try(Connection con=DatabaseConnection.getConnection(); PreparedStatement st=con.prepareStatement("select `description` from `unacloudpaas`.`puppetmodule` where `name` = ?;");){
         st.setString(1, name);
         try(ResultSet rs=st.executeQuery()){
            if(rs.next()){
               ret=new PuppetModule(name, rs.getString(1));
            }
         }
         if(ret!=null)try(PreparedStatement psParam=con.prepareStatement("SELECT `puppetparam`.`name`,`puppetparam`.`defaultValue` FROM `unacloudpaas`.`puppetparam` where `puppetparam`.`puppetmodule_name`=?;")){
            psParam.setString(1,name);
            try(ResultSet rs=psParam.executeQuery()){
               while(rs.next())ret.getParameters().add(new PuppetModuleParam(rs.getString(1),rs.getString(2)));
            }
         }
      }catch(SQLException ex){
         Logger.getLogger(PuppetModuleManager.class.getName()).log(Level.SEVERE, null, ex);
      }
      return ret;
   }
}
