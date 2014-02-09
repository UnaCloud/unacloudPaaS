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
import unacloud.paas.back.database.entities.Node;
import unacloud.paas.back.database.entities.PlatformExecution;
import unacloud.paas.back.database.entities.RolExecution;
import unacloud.paas.back.execution.RuntimeRoleExecutionBean;

import unacloud.paas.back.sshutils.ProcessManager;
import unacloud.paas.back.sshutils.SSHCommand;
import unacloud.paas.back.utils.PaaSUtils;

import static unacloud.paas.back.puppet.PuppetConstants.*;
import unacloud.paas.back.execution.entities.RuntimeExecutionLog;
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
      for(RolExecution role : platform.getRolExecution()){
         for(Node node : role.getNodes()){
            nodeNames.add(node.getHostname());
         }
      }
      agregarNodo(nodeNames.toArray(new String[0]));
      for(RolExecution role : platform.getRolExecution()){
         for(Node n : role.getNodes()){
            try(PrintWriter pw=new PrintWriter(new File(NODES_FOLDER, n.getHostname()+".pp"))){
               pw.println("node '"+n.getHostname()+"'{");
               for(PuppetModuleInstance module : n.getPuppetModuleInstances()){
                  pw.println(module.toString());
               }
               pw.println("}");
            }catch(FileNotFoundException ex){
               Logger.getLogger(PuppetMaster.class.getName()).log(Level.SEVERE, null, ex);
            }
         }
      }
      for(RolExecution role : platform.getRolExecution()){
         for(Node d : role.getNodes()){
            new SSHCommand(d, "echo \"certname="+d.getHostname().toLowerCase()+"\" >> /etc/puppet/puppet.conf", new RuntimeExecutionLog(platform.getId(), "node:"+d.getHostname())).waitFor();
         }
      }
      for(RolExecution role : platform.getRolExecution()){
          RuntimeRoleExecutionBean.executeCommandOnRole(platform, role, "puppet agent --test",5000);
      }
      PaaSUtils.sleep(10000);
      String nodeList="";
      for(int e=0;e<nodeNames.size();e++){
         nodeList+=" "+(nodeNames.get(e).toLowerCase());
         if(e>0&&e%10==0){
            new ProcessManager(new RuntimeExecutionLog(platform.getId(),"puppetMaster"), "puppet cert sign"+nodeList).waitFor();
            nodeList="";
         }
      }
      if(!nodeList.isEmpty())new ProcessManager(new RuntimeExecutionLog(platform.getId(),"puppetMaster"), "puppet cert sign"+nodeList).waitFor();
      Logger.getLogger("PaaS").log(Level.INFO, "puppetMaster {0}", nodeList);
      for(RolExecution role : platform.getRolExecution()){
          RuntimeRoleExecutionBean.executeCommandOnRole(platform, role, "puppet agent --test",5000);
      }
   }
   public static void stopPuppetCluster(List<Node> cluster){
      String nodeList="";
      for(int e=0;e<cluster.size();e++){
         nodeList+=" "+cluster.get(e).getHostname();
         if(e>0&&e%10==0){
            new ProcessManager(null, "puppet cert clean"+nodeList).waitFor();
            nodeList="";
         }
      }
      if(!nodeList.isEmpty())new ProcessManager(null, "puppet cert clean"+nodeList).waitFor();
   }
   private static String[] masterRsaId;
   public static String[] getMasterRSAId(){
      if(masterRsaId==null){
         return masterRsaId=PaaSUtils.readRsaId("/root/.ssh/id_rsa.pub");
      }
      return masterRsaId;
   }
}
