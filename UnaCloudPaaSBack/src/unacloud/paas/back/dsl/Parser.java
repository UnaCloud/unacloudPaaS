/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unacloud.paas.back.dsl;
import unacloud.paas.back.execution.PlatformExecution;
import unacloud.paas.back.execution.RoleExecution;
/**
 *
 * @author G
 */
public class Parser{
   public static String parseFromEnvironment(String text, RoleExecution role, PlatformExecution platform){
      
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
            ret+=procesarToken(token, role, platform);
            current=next;
         }
      }
      return ret;
   }
   private static String procesarToken(String token, RoleExecution role, PlatformExecution platform){
      String part[]=token.split("\\.");
      if(part.length==1){
         
      }else if(part.length==2){
         String h="roles: ";
         for(RoleExecution roleExe:platform.getRoles()){
            h+=roleExe.getRoleName()+",";
            if(roleExe.getRoleName().equalsIgnoreCase(part[0])){
               if(part[1].equalsIgnoreCase("cores"))return ""+roleExe.getRoleConfiguration().getCores();
               else if(part[1].equalsIgnoreCase("hostname"))return ""+roleExe.getNodes().get(0).getHostname();
            }
         }
      }else if(part.length==3){
         
      }
      return "$"+token;
   }
}
