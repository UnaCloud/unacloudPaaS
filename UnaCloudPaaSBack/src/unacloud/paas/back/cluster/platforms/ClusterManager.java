package unacloud.paas.back.cluster.platforms;
import unacloud.paas.back.iaasservices.ClusterServices;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import unacloud.paas.back.Main;
import unacloud.paas.back.database.DatabaseConnection;
import unacloud.paas.back.execution.ExecutionLog;
import unacloud.paas.back.execution.NodeExecution;
import unacloud.paas.back.execution.PlatformExecution;
import unacloud.paas.back.execution.RoleExecution;
import unacloud.paas.back.puppet.PuppetMaster;
import unacloud.paas.back.sshutils.ProcessManager;
import unacloud.paas.back.user.FolderManager;
import static unacloud.paas.back.user.FolderManager.PLATFORMS_FOLDER;
import unacloud.paas.back.utils.ThreadUtils;
import unacloud.paas.data.entities.enums.ExecutionState;
import unacloud.paas.data.execution.FileDescriptionEntity;
import unacloud.paas.data.execution.PlatformExecutionEntity;
import unacloud.paas.data.managers.PlatformExecutionManager;
public class ClusterManager{
   public static void createCluster(final PlatformExecutionEntity platformExecution, final String username, final List<FileDescriptionEntity> files){
      if(platformExecution==null){
         return;
      }
      if(!PlatformExecutionManager.createPlatformExecution(platformExecution, username)){
         return;
      }
      Main.start();
      startCluster(platformExecution, files);
   }
   public static void startCluster(final PlatformExecutionEntity platformExecution, final List<FileDescriptionEntity> files){
      ThreadUtils.pool.submit(new Runnable(){
         @Override
         public void run(){
            final long platformExecutionId=platformExecution.getId();
            FolderManager.createPlatformFolder(platformExecutionId);
            PlatformExecution execution=new PlatformExecution(platformExecutionId, platformExecution);
            PlatformExecutionManager.storeLog(new ExecutionLog(platformExecutionId, "platform", "Starting platform..."));
            execution.startPlatform();
            PlatformExecutionManager.addRoleExecution(platformExecutionId, execution.getRoles());
            PlatformExecutionManager.updatePlatformExecutionState(platformExecutionId, ExecutionState.STARTING.getId());
            PlatformExecutionManager.storeLog(new ExecutionLog(platformExecutionId, "platform", "Configuring platform..."));
            try{
               if(platformExecution.getPlatform().getId()==1){
                  try(PrintWriter pw=new PrintWriter(new File(PLATFORMS_FOLDER, "/"+Long.toHexString(platformExecutionId)+"/machinesFile"))){
                     for(RoleExecution re : execution.getRoles()){
                        for(NodeExecution node : re.getNodes()){
                           pw.println(node.getHostname());
                        }
                     }
                  }catch(FileNotFoundException ex){
                     Logger.getLogger(ClusterManager.class.getName()).log(Level.SEVERE, null, ex);
                  }
               }
               if(files!=null){
                  for(FileDescriptionEntity fde : files){
                     File dest=new File(new File(PLATFORMS_FOLDER, "/"+Long.toHexString(platformExecutionId)+"/"), fde.getPath()+(fde.getPath().endsWith("/")?"":"/")+fde.getName());
                     try(FileOutputStream fos=new FileOutputStream(dest); InputStream is=fde.getContent()){
                        byte[] buffer=new byte[512];
                        for(int n; (n=is.read(buffer))!=-1;){
                           fos.write(buffer, 0, n);
                        }
                     }catch(Exception ex){
                        Logger.getLogger(ClusterManager.class.getName()).log(Level.SEVERE, null, ex);
                     }
                     if(fde.isUnzip()){
                        new ProcessManager(null, "unzip "+dest.getAbsolutePath()+" -d "+dest.getParentFile().getAbsolutePath()).waitFor();
                        dest.delete();
                     }
                  }
               }
               execution.configurePlatform();
               PlatformExecutionManager.storeLog(new ExecutionLog(platformExecutionId, "platform", "Executing commands..."));
               execution.executeCommands();
               PlatformExecutionManager.storeLog(new ExecutionLog(platformExecutionId, "platform", "Executing commands 2..."));
               PlatformExecutionManager.updatePlatformExecutionState(platformExecutionId, ExecutionState.RUNNING.getId());
               PlatformExecutionManager.storeLog(new ExecutionLog(platformExecutionId, "platform", "Running..."));
            }catch(Throwable ex){
               stopCluster(platformExecutionId, ExecutionState.FAILED);
            }
         }
      });
   }
   public static void stopCluster(long platformExecutionId, ExecutionState state){
      PlatformExecutionManager.updatePlatformExecutionState(platformExecutionId, state.getId());
      PlatformExecutionManager.storeLog(new ExecutionLog(platformExecutionId, "platform", "Stopping "+state+"..."));
      List<NodeExecution> nodes=new ArrayList<>();
      try(Connection con=DatabaseConnection.getConnection(); Statement st=con.createStatement(); ResultSet rs=st.executeQuery("select hostname,iaasExecutionId from node where platformExecution_Id = "+platformExecutionId+";");){
         while(rs.next())
            nodes.add(new NodeExecution(rs.getString(1), null, rs.getString(2)));
      }catch(SQLException ex){
         Logger.getLogger(ClusterManager.class.getName()).log(Level.SEVERE, null, ex);
      }
      if(!nodes.isEmpty()){
         PuppetMaster.stopPuppetCluster(nodes);
         ClusterServices.stopCluster(nodes);
      }
   }
}
