/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unacloud.paas.back;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import unacloud.paas.back.database.DatabaseConnection;
import unacloud.paas.monitor.VirtualMachineMonitor;
/**
 *
 * @author G
 */
public class Main{
   public static void start(){
      VirtualMachineMonitor.startMonitor();
      
   }
   public static void main(String...args)throws Exception{
      Connection con=DatabaseConnection.getConnection();
      Statement st=con.createStatement();
      ResultSet rs=st.executeQuery("select * from platformExecution where `platformExecution`.`runName` like 'gromac%';");
      List<String> array=new ArrayList<>();
      while(rs.next()){
         array.add("\""+rs.getString("runName")+"\"");
         array.add("\""+Long.toHexString(rs.getLong("id"))+"\"");
      }
      System.out.println(Arrays.toString(array.toArray()));
      rs.close();st.close();con.close();
   }
           
}
