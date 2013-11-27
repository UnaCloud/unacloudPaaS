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
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import unacloud.paas.back.database.DatabaseConnection;
import unacloud.paas.back.execution.ExecutionLog;
import unacloud.paas.back.execution.NodeExecution;
import unacloud.paas.back.execution.RoleExecution;
import unacloud.paas.back.user.FolderManager;
import unacloud.paas.data.entities.LogEntity;
import unacloud.paas.data.entities.Platform;
import unacloud.paas.data.entities.PlatformRole;
import unacloud.paas.data.entities.PuppetModuleUsage;
import unacloud.paas.data.entities.enums.ExecutionState;
import unacloud.paas.data.entities.enums.MultiplicityType;
import unacloud.paas.data.execution.CommandExecutionEntity;
import unacloud.paas.data.execution.CommandWaitEntity;
import unacloud.paas.data.execution.NodeEntity;
import unacloud.paas.data.execution.PlatformExecutionEntity;
import unacloud.paas.data.execution.RoleExecutionEntity;
/**
 *
 * @author G
 */
public class PlatformExecutionManager{
   public static void updatePlatformExecutionState(long platformExecutionId, int state){
      try(Connection con=DatabaseConnection.getConnection(); Statement ps=con.createStatement();){
         ps.executeUpdate("update `platformExecution` set `executionState_state`="+state+" where `id`="+platformExecutionId+";");
      }catch(SQLException ex){
         Logger.getLogger(PlatformExecutionManager.class.getName()).log(Level.SEVERE, null, ex);
      }
   }
   public static PlatformExecutionEntity generateVoidPlatformExecution(int platformId){
      Platform plat=PlatformManager.getFullPlatform(platformId);
      PlatformExecutionEntity ret=new PlatformExecutionEntity();
      ret.setPlatform(plat);
      for(PlatformRole role : plat.getRoles()){
         RoleExecutionEntity roleExecution=new RoleExecutionEntity();
         roleExecution.setRoleConfig(role);
         if(role.getMultiplicity()==MultiplicityType.ONE){
            roleExecution.setCores(2);
            roleExecution.setCoresPerNode(2);
         }else{
            if(role.getMultiplicity()==MultiplicityType.MANY){
               roleExecution.setCores(4);
               roleExecution.setCoresPerNode(4);
            }
         }
         ret.getRoles().add(roleExecution);
      }
      for(RoleExecutionEntity role : ret.getRoles()){
         if(ret.getPlatform().getMainCommand()!=null&&role.getRoleConfig().getRoleName().equals(ret.getPlatform().getMainCommand().getRoleId())){
            
            CommandExecutionEntity command=new CommandExecutionEntity(ret.getPlatform().getMainCommand().getRoleId(), ret.getPlatform().getMainCommand().getCommand(), ret.getPlatform().getMainCommand().getMultiplicity(), ret.getPlatform().getMainCommand().getResourceType());
            command.setUser(ret.getPlatform().getMainCommand().getUser());
            command.setMainCommand(true);
            role.getCommandExecutions().add(command);
         }
      }
      return ret;
   }
   public static boolean createPlatformExecution(PlatformExecutionEntity platformExecution, String username){
      try(Connection con=DatabaseConnection.getConnection();){
         try(PreparedStatement ps=con.prepareStatement("INSERT INTO `platformExecution`(`platform_idplatform`,`user_username`,`runName`,`executionState_state`) VALUES (?,?,?,?);", Statement.RETURN_GENERATED_KEYS)){
            ps.setInt(1, platformExecution.getPlatform().getId());
            ps.setString(2, username);
            ps.setString(3, platformExecution.getRunName());
            ps.setInt(4, ExecutionState.CREATING.getId());
            ps.executeUpdate();
            try(ResultSet rs=ps.getGeneratedKeys()){
               if(rs.next()){
                  platformExecution.setId(rs.getLong(1));
               }
            }
         }
         try(PreparedStatement ps=con.prepareStatement("INSERT INTO `roleExecution`(`platformExecution_id`,`name`,`templateId`,`puppetModuleName`,`cores`,`coresPerVM`,`RAMperCore`) VALUES (?,?,?,?,?,?,?);")){
            for(RoleExecutionEntity role : platformExecution.getRoles()){
               ps.setLong(1, platformExecution.getId());
               ps.setString(2, role.getRoleConfig().getRoleName());
               ps.setInt(3, role.getRoleConfig().getTemplateId());
               ps.setString(4, role.getRoleConfig().getPuppetModule().getPuppetmodule().getName());
               ps.setInt(5, role.getCores());
               ps.setInt(6, role.getCoresPerNode());
               ps.setInt(7, role.getRAMperCore());
               ps.executeUpdate();
            }
         }
         try(PreparedStatement ps=con.prepareStatement("INSERT INTO `unacloudpaas`.`command` (`platformExecution_id`,`roleExecution_name`,`command`,`multiplicity`,`resourceType_id`) VALUES (?,?,?,?,?);")){
            for(RoleExecutionEntity role : platformExecution.getRoles()){
               for(CommandExecutionEntity command : role.getCommandExecutions()){
                  ps.setLong(1, platformExecution.getId());
                  ps.setString(2, role.getRoleConfig().getRoleName());
                  ps.setString(3, command.getCommand());
                  ps.setString(4, ""+command.getMultiplicity().getId());
                  ps.setInt(5, command.getResourceType().getValue());
                  ps.executeUpdate();
               }

            }
         }
         try(PreparedStatement ps=con.prepareStatement("INSERT INTO `unacloudpaas`.`puppetModuleUsage` (`puppetmodule_name`) VALUES (?);", Statement.RETURN_GENERATED_KEYS)){
            for(RoleExecutionEntity role : platformExecution.getRoles()){
               for(PuppetModuleUsage puppetModule : role.getPuppetModules()){
                  ps.setString(1, puppetModule.getPuppetmodule().getName());
                  ps.addBatch();
               }
            }
            ps.executeBatch();
            try(ResultSet rs=ps.getGeneratedKeys()){
               for(RoleExecutionEntity role : platformExecution.getRoles()){
                  for(PuppetModuleUsage puppetModule : role.getPuppetModules()){
                     rs.next();
                     puppetModule.setId(rs.getInt(1));
                  }
               }
            }
         }
         try(PreparedStatement ps=con.prepareStatement("INSERT INTO `unacloudpaas`.`roleExecution_has_puppetModuleUsage` (`roleExecution_platformExecution_id`,`roleExecution_name`,`puppetModuleUsage_id`) VALUES (?,?,?);", Statement.RETURN_GENERATED_KEYS)){
            for(RoleExecutionEntity role : platformExecution.getRoles()){
               for(PuppetModuleUsage puppetModule : role.getPuppetModules()){
                  ps.setLong(1, platformExecution.getId());
                  ps.setString(2, role.getRoleConfig().getRoleName());
                  ps.setLong(3, puppetModule.getId());
                  ps.executeUpdate();
               }
            }
         }
      }catch(SQLException ex){
         Logger.getLogger(PlatformExecutionManager.class.getName()).log(Level.SEVERE, null, ex);
         return false;
      }
      return true;
   }
   public static void addRoleExecution(long platformExecutionId, List<RoleExecution> roles){
      try(Connection con=DatabaseConnection.getConnection();){
         try(PreparedStatement ps=con.prepareStatement("INSERT INTO `node` (`hostname`,`ipAddress`,`iaasExecutionId`,`platformExecution_id`,`roleExecution_name`) VALUES (?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS)){
            for(RoleExecution role : roles){
               for(NodeExecution node : role.getNodes()){
                  ps.setString(1, node.getHostname());
                  ps.setString(2, node.getIpAddress());
                  ps.setString(3, node.getIaasExecutionId());
                  ps.setLong(4, platformExecutionId);
                  ps.setString(5, role.getRoleName());
                  ps.addBatch();
               }
            }
            ps.executeBatch();
            try(ResultSet rs=ps.getGeneratedKeys()){
               for(RoleExecution role : roles){
                  for(NodeExecution node : role.getNodes()){
                     if(rs.next()){
                        node.setId(rs.getLong(1));
                     }
                  }
               }
            }
         }
      }catch(SQLException ex){
         Logger.getLogger(PlatformExecutionManager.class.getName()).log(Level.SEVERE, null, ex);
      }
   }
   public static void addCommandsToPlatform(long platformExecutionId, List<CommandExecutionEntity> commands){
   }
   public synchronized static void storeLog(ExecutionLog log){
      if(log==null){
         return;
      }
      try(Connection con=DatabaseConnection.getConnection(); PreparedStatement ps=con.prepareStatement("INSERT INTO `executionLog` (`platformExecution_id`,`message`,`component`) VALUES(?,?,?);")){
         ps.setLong(1, log.getPlatformId());
         ps.setString(2, log.getLog().toString());
         ps.setString(3, log.getComponent());
         ps.executeUpdate();
      }catch(SQLException ex){
         Logger.getLogger(PlatformExecutionManager.class.getName()).log(Level.SEVERE, null, ex);
      }
      log.getLog().setLength(0);
   }
   public static List<PlatformExecutionEntity> getPlatformExecutionList(String username){
      List<PlatformExecutionEntity> ret=new ArrayList<>();
      try(Connection con=DatabaseConnection.getConnection();){
         try(PreparedStatement ps=con.prepareStatement("SELECT `platformExecution`.`id`,`platformExecution`.`platform_idplatform`,`platformExecution`.`runName`,`platform`.`name`,`waiterClass`,executionState_state FROM `platformExecution`,`platform` where `platform`.`idplatform` = `platformExecution`.`platform_idplatform` and `platformExecution`.`user_username`=? order by `platformExecution`.`id` desc;");){
            ps.setString(1, username);
            try(ResultSet rs=ps.executeQuery()){
               while(rs.next()){
                  PlatformExecutionEntity pee=new PlatformExecutionEntity(rs.getLong(1), rs.getString(3), new Platform(rs.getString(4), rs.getInt(2), null,rs.getString(5)));
                  pee.setState(rs.getInt(6));
                  ret.add(pee);
               }
            }
         }
      }catch(SQLException ex){
         Logger.getLogger(PlatformExecutionManager.class.getName()).log(Level.SEVERE, null, ex);
      }
      return ret;
   }
   public static List<PlatformExecutionEntity> getRunningPlatformExecutionList(String username){
      List<PlatformExecutionEntity> ret=new ArrayList<>();
      try(Connection con=DatabaseConnection.getConnection();){
         try(PreparedStatement ps=con.prepareStatement("SELECT `platformExecution`.`id`,`platformExecution`.`platform_idplatform`,`platformExecution`.`runName`,`platform`.`name`,`executionState`.`state`,`executionState`.`name`,`waiterClass` FROM `platformExecution`,`platform`,`executionState` where `platform`.`idplatform` = `platformExecution`.`platform_idplatform` and `platformExecution`.`user_username`=? and `executionState_state`<="+ExecutionState.RUNNING.getId()+" and `executionState`.`state`=`platformExecution`.`executionState_state`;");){
            ps.setString(1, username);
            try(ResultSet rs=ps.executeQuery()){
               while(rs.next())
                  ret.add(new PlatformExecutionEntity(rs.getLong(1), rs.getString(3), new Platform(rs.getString(4), rs.getInt(2), null,rs.getString(7)), rs.getInt(5), rs.getString(6)));
            }
         }
      }catch(SQLException ex){
         Logger.getLogger(PlatformExecutionManager.class.getName()).log(Level.SEVERE, null, ex);
      }
      return ret;
   }
   public static int getRunningExecutionPlatformsCount(String username){
      try(Connection con=DatabaseConnection.getConnection(); PreparedStatement ps=con.prepareStatement("select count(*) from `platformExecution` where `user_username`=? and `executionState_state`<="+ExecutionState.RUNNING.getId()+";");){
         ps.setString(1, username);
         try(ResultSet rs=ps.executeQuery();){
            if(rs.next()){
               return rs.getInt(1);
            }
         }
      }catch(SQLException ex){
         Logger.getLogger(PlatformExecutionManager.class.getName()).log(Level.SEVERE, null, ex);
      }
      return -1;
   }
   public static PlatformExecutionEntity createPlatformExecution(int platformId){
      Platform platform=PlatformManager.getFullPlatform(platformId);
      PlatformExecutionEntity ret=new PlatformExecutionEntity(0, "", platform);
      return ret;
   }
   public static PlatformExecutionEntity getPlatformExecution(long platformExecutionId){
      PlatformExecutionEntity ret=null;
      try(Connection con=DatabaseConnection.getConnection(); Statement st=con.createStatement();){
         try(ResultSet rs=st.executeQuery("SELECT `platformExecution`.`platform_idplatform`,`platformExecution`.`runName`,`platform`.`name`,`executionState`.`state`,`executionState`.`name`,`platform`.`imagename`,`waiterClass` FROM `platformExecution`,`platform`,`executionState` where `platform`.`idplatform` = `platformExecution`.`platform_idplatform` and `platformExecution`.`id`="+platformExecutionId+" and `executionState`.`state`=`platformExecution`.`executionState_state`;");){
            if(rs.next()){
               ret=new PlatformExecutionEntity(platformExecutionId, rs.getString(2), new Platform(rs.getString(3), rs.getInt(1), rs.getString(6),rs.getString(7)), rs.getInt(4), rs.getString(5));
            }
         }
         if(ret!=null){
            Map<String, RoleExecutionEntity> roles=new TreeMap<>();
            try(ResultSet rs=st.executeQuery("SELECT `roleExecution`.`name`,`roleExecution`.`cores`,`roleExecution`.`coresPerVM`,`roleExecution`.`RAMperCore`,`roleExecution`.`tamano` FROM `unacloudpaas`.`roleExecution` where `roleExecution`.`platformExecution_id`="+platformExecutionId+";")){
               while(rs.next()){
                  RoleExecutionEntity role=new RoleExecutionEntity(rs.getInt(5), rs.getInt(2), rs.getInt(3), PlatformManager.getRole(rs.getString(1), ret.getPlatform().getId(), con));
                  roles.put(role.getRoleConfig().getRoleName(), role);
                  ret.getRoles().add(role);
               }
            }
            try(ResultSet rs=st.executeQuery("SELECT `node`.`hostname`,`node`.`ipAddress`,`node`.`roleExecution_name`,`node`.`maxSecuentialFailCount`,`idNode` FROM `unacloudpaas`.`node` where `node`.`platformExecution_id`="+platformExecutionId+";")){
               while(rs.next()){
                  NodeEntity node=new NodeEntity(rs.getLong(5),rs.getString(1), rs.getString(2),rs.getString(3));
                  node.setNoConnectionCount(rs.getInt(4));
                  roles.get(rs.getString(3)).getNodes().add(node);
               }
            }
            try(PreparedStatement ps=con.prepareStatement("SELECT `commandWait`.`processId`,`commandWait`.`executionState_state` FROM `unacloudpaas`.`commandWait` where `commandWait`.`executionState_state`="+ExecutionState.RUNNING.getId()+" and `commandWait`.`idNode`=?;")){
               for(RoleExecutionEntity role : roles.values()){
                  for(NodeEntity ne : role.getNodes()){
                     ps.setLong(1, ne.getId());
                     try(ResultSet psRs=ps.executeQuery()){
                        while(psRs.next())ne.getWatingCommands().add(new CommandWaitEntity(ne.getId(), psRs.getLong(1), ExecutionState.getFromId(psRs.getInt(2))));
                     }
                  }
               }
            }
         }
      }catch(SQLException ex){
         Logger.getLogger(PlatformExecutionManager.class.getName()).log(Level.SEVERE, null, ex);
      }
      return ret;
   }
   public static void addCommandWait(long nodeId, long processId){
      try(Connection con=DatabaseConnection.getConnection(true); Statement st=con.createStatement()){
         st.executeUpdate("INSERT INTO `unacloudpaas`.`commandWait` (`idNode`,`processId`,`executionState_state`) VALUES ("+nodeId+","+processId+","+ExecutionState.RUNNING.getId()+");");
      }catch(SQLException ex){
         Logger.getLogger(PlatformExecutionManager.class.getName()).log(Level.SEVERE, null, ex);
      }
   }
   public static void deletePlatformExecution(long platformExecutionId){
      System.out.println("deletePlatformExecution "+platformExecutionId);
      try(Connection con=DatabaseConnection.getConnection();Statement st=con.createStatement();){
         st.executeUpdate("delete from executionLog where platformExecution_id="+platformExecutionId+";");
         st.executeUpdate("delete from node where platformExecution_id="+platformExecutionId+";");
         st.executeUpdate("delete from command where platformExecution_id="+platformExecutionId+";");
         st.executeUpdate("delete from roleExecution_has_puppetModuleUsage where roleExecution_platformExecution_id="+platformExecutionId+";");
         st.executeUpdate("delete from roleExecution where platformExecution_id="+platformExecutionId+";");
         st.executeUpdate("delete from platformExecution where id="+platformExecutionId+";");
         con.commit();
      }catch(SQLException ex){
         Logger.getLogger(PlatformExecutionManager.class.getName()).log(Level.SEVERE, null, ex);
      }
      FolderManager.deletePlatformFolder(platformExecutionId);
   }
   public static List<LogEntity> getPlatformLog(long platformId){
      List<LogEntity> ret=new ArrayList<>();
      try(Connection con=DatabaseConnection.getConnection();Statement st=con.createStatement();ResultSet rs=st.executeQuery("SELECT `executionLog`.`time`,`executionLog`.`message`,`executionLog`.`component` FROM `unacloudpaas`.`executionLog` where `executionLog`.`platformExecution_id`="+platformId+" order by `executionLog`.`idnodeLog` desc;");){
         while(rs.next()){
            ret.add(new LogEntity(platformId,rs.getString(3),rs.getString(2),rs.getTimestamp(1),null));
         }
      }catch(SQLException ex){
         Logger.getLogger(PlatformExecutionManager.class.getName()).log(Level.SEVERE, null, ex);
      }
      if(!ret.isEmpty()){
         long time=ret.get(ret.size()-1).getTime().getTime();
         for(LogEntity log:ret){
            long rest=(log.getTime().getTime()-time)/1000l;
            log.setRelativeTime((rest/60)+":"+padd(rest%60));
         }
      }
      return ret;
   }
   private static String padd(long l){
      String h=""+l;
      while(h.length()<2)h="0"+h;
      return h;
   }
           
}