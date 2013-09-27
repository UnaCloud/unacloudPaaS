package unacloud.paas.back.puppet;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import unacloud.paas.back.execution.PuppetModuleInstance;

import unacloud.paas.back.sshutils.ProcessManager;
import unacloud.paas.back.sshutils.SSHCommand;
import unacloud.paas.back.utils.PaaSUtils;

import unacloud.paas.back.execution.NodeExecution;

import static puppet.PuppetConstants.*;
import unacloud.paas.back.execution.ExecutionLog;
import unacloud.paas.back.execution.PlatformExecution;
import unacloud.paas.back.execution.RoleExecution;
public class PuppetMaster{
   public static synchronized void agregarNodo(String... nodeNames){
      Set<String> toReview=new TreeSet<>();
      Set<String> toRemove=new TreeSet<>();
      Collections.addAll(toReview, nodeNames);
      File sitepp=new File(NODES_FOLDER, "site.pp");
      try(BufferedReader br=new BufferedReader(new FileReader(sitepp))){
         for(String h; (h=br.readLine())!=null;){
            for(String node : nodeNames){
               if(h.contains("import \""+node+"\"")){
                  toRemove.add(node);
               }
            }
         }
      }catch(Exception ex){
         Logger.getLogger(PuppetMaster.class.getName()).log(Level.SEVERE, null, ex);
      }
      toReview.removeAll(toRemove);
      if(!toReview.isEmpty()){
         try(PrintWriter pw=new PrintWriter(new FileOutputStream(sitepp, true))){
            for(String node : toReview){
               pw.println("import \""+node+"\"");
            }
         }catch(FileNotFoundException ex){
            Logger.getLogger(PuppetMaster.class.getName()).log(Level.SEVERE, null, ex);
         }
      }
      for(String node : toReview){
         createPuppetNode(node);
      }
   }
   private static void createPuppetNode(String nodeName){
      try(PrintWriter pw=new PrintWriter(new File(NODES_FOLDER, nodeName+".pp"))){
         pw.println("node '"+nodeName+"'{");
         pw.println("}");
      }catch(FileNotFoundException ex){
         Logger.getLogger(PuppetMaster.class.getName()).log(Level.SEVERE, null, ex);
      }
   }
   public static void configurePuppetCluster(PlatformExecution platform){
      List<String> nodeNames=new LinkedList<>();
      for(RoleExecution role : platform.getRoles()){
         for(NodeExecution node : role.getNodes()){
            nodeNames.add(node.getHostname());
         }
      }
      agregarNodo(nodeNames.toArray(new String[0]));
      for(RoleExecution role : platform.getRoles()){
         for(NodeExecution n : role.getNodes()){
            try(PrintWriter pw=new PrintWriter(new File(NODES_FOLDER, n.getHostname()+".pp"))){
               pw.println("node '"+n.getHostname()+"'{");
               for(PuppetModuleInstance module : n.getModules()){
                  pw.println(module.toString());
               }
               pw.println("}");
            }catch(FileNotFoundException ex){
               Logger.getLogger(PuppetMaster.class.getName()).log(Level.SEVERE, null, ex);
            }
         }
      }
      for(RoleExecution role : platform.getRoles()){
         for(NodeExecution d : role.getNodes()){
            new SSHCommand(d, "echo \"certname="+d.getHostname()+"\" >> /etc/puppet/puppet.conf", new ExecutionLog(platform.getPlatformExecutionId(), "node:"+d.getHostname())).waitFor();
         }
      }
      for(RoleExecution role : platform.getRoles()){
         role.executeCommandOnRole("puppet agent --test",5000);
      }
      String nodeList="";
      for(int e=0;e<nodeNames.size();e++){
         for(String d : nodeNames){
            nodeList+=" "+d;
         }
         if(e>0&&e%20==0){
            new ProcessManager(null, "puppet cert sign"+nodeList).waitFor();
            nodeList="";
         }
      }
      if(!nodeList.isEmpty())new ProcessManager(null, "puppet cert sign"+nodeList).waitFor();
      for(RoleExecution role : platform.getRoles()){
         role.executeCommandOnRole("puppet agent --test",10000);
      }
   }
   public static void stopPuppetCluster(List<NodeExecution> cluster){
      String nodeList="";
      {
         for(NodeExecution d : cluster){
            nodeList+=" "+d.getHostname();
         }
      }
      new ProcessManager(null, "puppet cert clean"+nodeList).waitFor();
   }
   private static String[] masterRsaId;
   public static String[] getMasterRSAId(){
      if(masterRsaId==null){
         return masterRsaId=PaaSUtils.readRsaId("/root/.ssh/id_rsa.pub");
      }
      return masterRsaId;
   }
}
