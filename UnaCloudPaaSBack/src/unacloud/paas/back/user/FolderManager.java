/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unacloud.paas.back.user;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import unacloud.paas.back.database.DatabaseConnection;
import unacloud.paas.back.sshutils.ProcessManager;
import unacloud.paas.back.utils.PaaSUtils;
/**
 *
 * @author G
 */
public class FolderManager{
   public static final File PLATFORMS_FOLDER=new File("/unacloud/platforms");
   public static final File USERS_FOLDER=new File("/unacloud/users");
   public static void createPlatformFolder(long platformExecutionId){
      new File(PLATFORMS_FOLDER, Long.toHexString(platformExecutionId)).mkdirs();
      new ProcessManager(null, "chmod 777 "+PLATFORMS_FOLDER+"/"+Long.toHexString(platformExecutionId)).waitFor();
      //lnDirectory(new File("/root/users/default"),new File("/root/users/platforms/"+Long.toHexString(platformExecutionId)));
   }
   public static File getPlatformFolder(long platformExecutionId){
      return new File(PLATFORMS_FOLDER, Long.toHexString(platformExecutionId));
   }
   public static void createUserFolder(String username){
      new File(USERS_FOLDER, "/"+PaaSUtils.getMd5(username)).mkdirs();
   }
   private static void lnDirectory(File source, File dest){
      if(!source.isDirectory()){
         return;
      }
      File[] files=source.listFiles();
      if(files!=null){
         for(File f : files){
            ln(f, dest);
         }
      }
   }
   private static void ln(File source, File dest){
      if(source.isFile()){
         ln(source.getAbsolutePath(), dest.getAbsolutePath());
      }else{
         if(source.isDirectory()){
            File newDest=new File(dest, source.getName());
            newDest.mkdir();
            File[] files=source.listFiles();
            if(files!=null){
               for(File f : files){
                  ln(f, newDest);
               }
            }
         }
      }
   }
   private static void ln(String f1, String f2){
      new ProcessManager(null, "ln "+f1+" "+f2).waitFor();
   }
   public static void uploadUserFile(String path, String name, String username, InputStream content){
      createUserFolder(username);
      File dest=getFile(name, path, username);
      path=normalizePath(path);name=normalizeName(name);
      dest.getParentFile().mkdirs();
      try(FileOutputStream fos=new FileOutputStream(dest);InputStream is=content){
         byte[] buff=new byte[10240];
         for(int n;(n=is.read(buff))!=-1;)fos.write(buff,0,n);
      }catch(Exception ex){
         Logger.getLogger(FolderManager.class.getName()).log(Level.SEVERE, null, ex);
         return;
      }
      try(Connection con=DatabaseConnection.getConnection(); PreparedStatement ps=con.prepareStatement("insert into `userFile` (`name`,`path`,`user_username`) values(?,?,?);")){
         ps.setString(1, name);
         ps.setString(2, path);
         ps.setString(3, username);
         ps.executeUpdate();
      }catch(SQLException ex){
         Logger.getLogger(FolderManager.class.getName()).log(Level.SEVERE, null, ex);
      }
   }
   public static void deleteUserFile(String path, String name, String username){
      getFile(name, path, username).delete();
      name=normalizeName(name);
      path=normalizePath(path);
      try(Connection con=DatabaseConnection.getConnection(); PreparedStatement ps=con.prepareStatement("delete from `userFile` where `name`=? and `path`=? and `user_username`=?;")){
         ps.setString(1, name);
         ps.setString(2, path);
         ps.setString(3, username);
         ps.executeUpdate();
      }catch(SQLException ex){
         Logger.getLogger(FolderManager.class.getName()).log(Level.SEVERE, null, ex);
      }
   }
   public static List<UserFileEntity> getUserFiles(String username){
      List<UserFileEntity> ret=new ArrayList<>();
      try(Connection con=DatabaseConnection.getConnection(); PreparedStatement ps=con.prepareStatement("select `name`,`path` from `userFile` where `user_username`=?;")){
         ps.setString(1, username);
         try(ResultSet rs=ps.executeQuery();){
            while(rs.next())
               ret.add(new UserFileEntity(rs.getString(1), rs.getString(2)));
         }

      }catch(SQLException ex){
         Logger.getLogger(FolderManager.class.getName()).log(Level.SEVERE, null, ex);
      }
      return ret;
   }
   public static File getFile(String name,String path,String username){
      path=normalizePath(path);
      name=normalizeName(name);
      return new File(new File(USERS_FOLDER+"/"+PaaSUtils.getMd5(username), path.startsWith("/")?path.substring(1):path), name);
   }
   private static String normalizeName(String name){
      return name.replace("/","").replace("..", "");
   }
   private static String normalizePath(String path){
      return path.replace("/..", "/").replaceAll("/+","/");
   }
   public static void deletePlatformFolder(long platformId){
      File f=getPlatformFolder(platformId);
      try{
         Runtime.getRuntime().exec("rm -r "+f.getAbsolutePath()).waitFor();
      }catch(IOException | InterruptedException ex){
         Logger.getLogger(FolderManager.class.getName()).log(Level.SEVERE, null, ex);
      }
   }
}