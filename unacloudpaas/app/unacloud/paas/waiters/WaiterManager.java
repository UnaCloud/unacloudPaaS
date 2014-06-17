package unacloud.paas.waiters;

import models.PlatformExecution;
import models.enums.ExecutionState;
import unacloud.paas.back.beans.ClusterManagerBean;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
public class WaiterManager{
   private static final Map<String, Waiter> waiters=new TreeMap<>();
   public static void checkTermination(final PlatformExecution platformExecution){
      if(platformExecution!=null&&platformExecution.getPlatform().getWaiterClass()!=null){
            Waiter w=getWaiter(platformExecution.getPlatform().getWaiterClass());
            if(w!=null&&w.hasEnded(platformExecution)){
                System.out.println("Deteniendo plataforma"+platformExecution.getHexId()+" "+platformExecution.getId()+" en "+new Date());
                new ClusterManagerBean().stopCluster(platformExecution, ExecutionState.SUCCESS);
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