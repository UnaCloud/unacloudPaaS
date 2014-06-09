package unacloud.paas.back.puppet;

import models.ExecutionLog;
import models.Node;
import models.PlatformExecution;
import models.RolExecution;
import unacloud.paas.back.execution.RuntimeRoleExecutionBean;
import unacloud.paas.back.sshutils.ProcessManager;
import unacloud.paas.back.sshutils.SSHCommand;
import unacloud.paas.back.utils.PaaSUtils;

import java.io.*;
import java.util.*;

import static unacloud.paas.back.puppet.PuppetConstants.*;
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
          ex.printStackTrace();
      }
      toReview.removeAll(toRemove);
      if(!toReview.isEmpty()){
         try(PrintWriter pw=new PrintWriter(new FileOutputStream(sitepp, true))){
            for(String node : toReview){
               pw.println("import \""+node+"\"");
            }
         }catch(FileNotFoundException ex){
             ex.printStackTrace();
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
          ex.printStackTrace();
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
                ex.printStackTrace();
            }
         }
      }
      for(RolExecution role : platform.getRolExecution()){
         for(Node d : role.getNodes()){
            new SSHCommand(d, "echo \"certname="+d.getHostname().toLowerCase()+"\" >> /etc/puppet/puppet.conf", new ExecutionLog(platform.getId(), d.getId(), "node:"+d.getHostname())).waitFor();
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
            new ProcessManager(new ExecutionLog(platform.getId(), null,"puppetMaster"), "puppet cert sign"+nodeList).waitFor();
            nodeList="";
         }
      }
      if(!nodeList.isEmpty())new ProcessManager(new ExecutionLog(platform.getId(), null,"puppetMaster"), "puppet cert sign"+nodeList).waitFor();
      System.out.println("puppetMaster "+ nodeList);
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
