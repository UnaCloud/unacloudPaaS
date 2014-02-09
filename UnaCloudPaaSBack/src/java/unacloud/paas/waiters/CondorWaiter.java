package unacloud.paas.waiters;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import unacloud.paas.back.database.entities.Node;
import unacloud.paas.back.database.entities.PlatformExecution;
import unacloud.paas.back.database.entities.RolExecution;
public class CondorWaiter implements Waiter{
   @Override
   public boolean hasEnded(PlatformExecution platform){
      for(RolExecution role : platform.getRolExecution()){
         if(role.getRol().getName().equals("condormaster")){
            for(Node node : role.getNodes()){
               try{
                  Process p=Runtime.getRuntime().exec("ssh root@"+node.getHostname()+" condor_q | tail -n 1");
                  try(BufferedReader br=new BufferedReader(new InputStreamReader(p.getInputStream()))){
                     String result=br.readLine();
                     for(String h; (h=br.readLine())!=null;){
                        result=h;
                     }
                     if(result!=null){
                        final String[] r=result.split(";|,");
                        for(int e=0; e<r.length; e++){
                           r[e]=r[e].trim().split("[ |\t]+")[0];
                        }
                        final int[] jobs=new int[r.length];
                        for(int e=0; e<r.length; e++){
                           if(r[e].matches("[0-9]+")){
                              jobs[e]=Integer.parseInt(r[e]);
                           }
                        }
                        if(jobs.length>5&&jobs[0]==0&&jobs[4]==0){
                           return true;
                        }
                     }else{
                        return true;
                     }
                  }catch(IOException ex){
                     Logger.getLogger(CondorWaiter.class.getName()).log(Level.SEVERE, null, ex);
                  }
                  try(BufferedReader br=new BufferedReader(new InputStreamReader(p.getErrorStream()))){
                     for(String h; (h=br.readLine())!=null;){
                        System.out.println("err "+h);
                     }
                  }
                  p.waitFor();
               }catch(InterruptedException|IOException ex){
                  Logger.getLogger(CondorWaiter.class.getName()).log(Level.SEVERE, null, ex);
               }
            }
         }
      }
      return false;
   }
}
