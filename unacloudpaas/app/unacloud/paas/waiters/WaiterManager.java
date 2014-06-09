package unacloud.paas.waiters;

import models.PlatformExecution;
import unacloud.paas.failrecovery.PlatformSucessManager;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.TreeMap;
public class WaiterManager{
   private static final Map<String, Waiter> waiters=new TreeMap<>();
   public static void checkTermination(final PlatformExecution platform){
      if(platform!=null&&platform.getPlatform().getWaiterClass()!=null){
            Waiter w=getWaiter(platform.getPlatform().getWaiterClass());
            System.out.println("Waiter: "+w+" "+platform.getPlatform().getWaiterClass());
            if(w!=null&&w.hasEnded(platform)){
                PlatformSucessManager.onPlatformSucess(platform.getId());
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
             ex.printStackTrace();
         }
         waiters.put(name, ret);
      }
      return ret;
   }
}