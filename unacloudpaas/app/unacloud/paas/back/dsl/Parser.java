package unacloud.paas.back.dsl;

import models.PlatformExecution;
import models.RolExecution;
public class Parser{
   public static String parseFromEnvironment(String text, RolExecution role, PlatformExecution platformExecution){
      
      if(text==null)return null;
      int current=0;
      String ret="";
      while(current<text.length()){
         int next=text.indexOf("$", current);
         if(next==-1){
            ret+=text.substring(current);
            break;
         }else{
            ret+=text.substring(current, next);
            current=next;
            next+=1;
            for(char c=text.charAt(next);Character.isDigit(c)||Character.isLetter(c)||c=='.';c=text.charAt(next))next++;
            if(next==-1){
               next=text.length();
            }
            String token=text.substring(current+1, next);
            ret += procesarToken(token, role, platformExecution);
            current=next;
         }
      }
      return ret;
   }
   private static String procesarToken(String token, RolExecution role, PlatformExecution platform){
      String part[]=token.split("\\.");
      if(part.length==1){
         
      }else if(part.length==2){
         String h="roles: ";
         for(RolExecution roleExe:platform.rolExecution){
            h+=roleExe.rol.name+",";
            if(roleExe.rol.name.equalsIgnoreCase(part[0])){
               if(part[1].equalsIgnoreCase("cores"))return ""+roleExe.getCores();
               if(part[1].equalsIgnoreCase("coresXVM"))return ""+roleExe.coresPerVM;
               else if(part[1].equalsIgnoreCase("hostname"))return ""+roleExe.nodes.get(0).hostname;
            }
         }
      }else if(part.length==3){
         
      }
      return "$"+token;
   }
}

