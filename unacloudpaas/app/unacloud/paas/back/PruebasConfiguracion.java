package unacloud.paas.back;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
public class PruebasConfiguracion{
   static Map<String,String> map=new TreeMap<>();
   public static void main(Connection con2,String... args) throws Exception{
      System.out.println("\nPlatf\tCores\tStart\tConf\trunTime");
      List<String> array=new ArrayList<>();
      try(Connection con=con2;Statement st=con.createStatement();){//DatabaseConnection.getConnection(); Statement st=con.createStatement();){
         System.out.println("Con");
         try(ResultSet rs=st.executeQuery("select * from platformExecution where `platformExecution`.`runName` like 't_%' order by `platformExecution`.`runName`;")){
            while(rs.next()){
               long start=0,conf=0,run=0,stop=0;
               String runName=rs.getString("runName");
               if(!runName.startsWith("t_"))continue;
               try(Statement st2=con.createStatement();ResultSet log=st2.executeQuery("SELECT time,SUBSTRING(message,1,30) as mensaje FROM `unacloudpaas`.`executionLog` where `executionLog`.`platformExecution_id`="+rs.getLong("id")+";")){
                  while(log.next()){
                     String h=log.getString("mensaje");
                     if(h.startsWith("Starting"))start=log.getTimestamp("time").getTime();
                     if(h.startsWith("Configuring"))conf=log.getTimestamp("time").getTime();
                     if(h.startsWith("Running"))run=log.getTimestamp("time").getTime();
                     if(h.startsWith("Stopping"))stop=log.getTimestamp("time").getTime();
                  }
               }
               
               array.add("\""+rs.getString("runName")+"\"");
               array.add("\""+Long.toHexString(rs.getLong("id"))+"\"");
               stop-=run;
               run-=conf;
               conf-=start;
               conf/=1000;run/=1000;stop/=1000;
               String[] j=runName.split("_");
               String add=map.get(runName);
               if(add==null)add=j[1]+"\t"+j[2];
               add+="\t"+conf+"\t"+run+"\t"+stop;
               map.put(runName,add);
               System.out.println(rs.getLong("id")+" "+j[1]+"\t"+j[2]+"\t"+conf+"\t"+run+"\t"+stop);
            }
         }
      }
      System.out.println(Arrays.toString(array.toArray()));
      System.out.println("\nPlatf\tCores\tStart\tConf\trunTime");
      for(String h:map.values())System.out.println(h);
      System.out.println("\nPlatf\tCores\tStart\tConf\trunTime");
      for(String h:map.values()){
         String[]j=h.split("\t");
         int[] vs=new int[3];
         for(int e=2;e<j.length;e++)vs[(e-2)%3]+=Integer.parseInt(j[e]);
         for(int e=0;e<vs.length;e++)vs[e]/=((j.length-2)/3);
         System.out.println(j[0]+"\t"+j[1]+"\t"+vs[0]+"\t"+vs[1]+"\t"+vs[2]);
      }
   }
}