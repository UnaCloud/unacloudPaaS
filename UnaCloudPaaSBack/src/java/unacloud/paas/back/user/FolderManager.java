/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unacloud.paas.back.user;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import unacloud.paas.back.database.entities.User;
import unacloud.paas.back.database.entities.UserFile;
import unacloud.paas.back.sshutils.ProcessManager;
import unacloud.paas.back.utils.PaaSUtils;

@Stateless
@LocalBean
public class FolderManager{
    @PersistenceContext()
    private EntityManager entityManager;
    
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
   public void uploadUserFile(String path, String name, String username, InputStream content){
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
      User user=entityManager.find(User.class,username);
      UserFile userFile=new UserFile(name, path);
      userFile.setUser(user);
      entityManager.persist(user);
   }
   public void deleteUserFile(long fileId, String username){
      UserFile userFile=entityManager.find(UserFile.class,fileId);
      if(userFile.getUser()!=null&&userFile.getUser().getUsername().equals(username)){
          getFile(userFile.getName(), userFile.getPath(), username).delete();
      }
   }
   public List<UserFile> getUserFiles(String username){
      User user=entityManager.find(User.class,username);
      if(user!=null){
          return user.getUserFiles();
      }
      return Collections.EMPTY_LIST;
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