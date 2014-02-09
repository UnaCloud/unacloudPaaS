/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unacloud.paas.back;
import unacloud.paas.monitor.VirtualMachineMonitor;
/**
 *
 * @author G
 */
public class Main{
   public static void start(){
      VirtualMachineMonitor.startMonitor();
   }
}
