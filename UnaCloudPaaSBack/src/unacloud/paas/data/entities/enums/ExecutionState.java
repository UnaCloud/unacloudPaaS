/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unacloud.paas.data.entities.enums;
/**
 *
 * @author G
 */
public enum ExecutionState{
   CREATING(1), STARTING(2), RUNNING(3), SUCCESS(4), FAILED(5), CANCELED(6), RECOVERING(7);
   int id;
   public int getId(){
      return id;
   }
   private ExecutionState(int id){
      this.id=id;
   }
   public static ExecutionState getFromId(int id){
      switch(id){
         case 1:
            return CREATING;
         case 2:
            return STARTING;
         case 3:
            return RUNNING;
         case 4:
            return SUCCESS;
         case 5:
            return FAILED;
         case 6:
            return CANCELED;
         case 7:
            return RECOVERING;
         default:
            return FAILED;
      }
   }
}
