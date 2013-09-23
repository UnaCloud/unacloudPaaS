package unacloud.paas.waiters;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import unacloud.paas.data.execution.PlatformExecutionEntity;
import unacloud.paas.failrecovery.PlatformSucessManager;
public class WaiterManager{
   private static final Map<String, Waiter> waiters=new TreeMap<>();
   public static void checkTermination(final PlatformExecutionEntity platform){
      if(platform!=null&&platform.getPlatform().getWaiterClass()!=null){
         Waiter w=getWaiter(platform.getPlatform().getWaiterClass());
         if(w!=null){
            if(w.hasEnded(platform)){
               PlatformSucessManager.onPlatformSucess(platform.getId());
            }
         }
      }
   }
   private static Waiter getWaiter(final String name){
      Waiter ret=waiters.get(name);
      if(ret==null){
         try{
            Class c=Class.forName(name);
            Object temp=c.getConstructor().newInstance();
            if(temp!=null&&temp instanceof Waiter){
               ret=(Waiter) temp;
            }
         }catch(ClassNotFoundException|NoSuchMethodException|SecurityException|InstantiationException|IllegalAccessException|IllegalArgumentException|InvocationTargetException ex){
            Logger.getLogger(WaiterManager.class.getName()).log(Level.SEVERE, null, ex);
         }
         waiters.put(name, ret);
      }
      return ret;
   }
}