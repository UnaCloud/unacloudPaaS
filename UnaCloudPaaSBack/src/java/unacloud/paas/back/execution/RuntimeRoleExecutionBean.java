/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unacloud.paas.back.execution;

import unacloud.paas.back.execution.entities.RuntimeExecutionLog;
import java.io.File;
import java.io.PrintWriter;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import unacloud.paas.back.beans.PlatformExecutionManagerBean;
import unacloud.paas.back.database.entities.Command;
import unacloud.paas.back.database.entities.Node;
import unacloud.paas.back.database.entities.PlatformExecution;
import unacloud.paas.back.database.entities.PuppetModuleUsage;
import unacloud.paas.back.database.entities.RolExecution;
import unacloud.paas.back.dsl.Parser;
import unacloud.paas.back.sshutils.SCP;
import unacloud.paas.back.sshutils.SSHCommand;
import unacloud.paas.back.sshutils.SSHCommandNoWait;
import unacloud.paas.back.user.FolderManager;
import unacloud.paas.back.utils.PaaSUtils;
import unacloud.paas.back.database.enums.MultiplicityType;
import unacloud.paas.back.database.enums.ResourceType;
import unacloud.paas.back.puppet.PuppetModuleInstance;
@Stateless
@LocalBean
public class RuntimeRoleExecutionBean {
    
    @EJB
    private PlatformExecutionManagerBean platformExecutionManagerBean;
    
    public void configureRole(PlatformExecution plaformConfiguration,RolExecution roleConfiguration){
        for (Node d : roleConfiguration.getNodes()){
            d.getPuppetModuleInstances().add(new PuppetModuleInstance("nfsunacloud").addValue("path", "'codd.sis.virtual.uniandes.edu.co:/unacloud/" + Long.toHexString(plaformConfiguration.getId()) + "'"));
        }
        if (roleConfiguration.getRol().getPuppetModule() != null) {
            PuppetModuleInstance instance = roleConfiguration.getRol().getPuppetModule().getpuppetModuleInstance(roleConfiguration,plaformConfiguration);
            for (Node d : roleConfiguration.getNodes()){
                d.getPuppetModuleInstances().add(instance);
            }
        }
        for (PuppetModuleUsage pmu : roleConfiguration.getPuppetModuleUsage()) {
            PuppetModuleInstance instance = pmu.getpuppetModuleInstance(roleConfiguration,plaformConfiguration);
            for (Node d : roleConfiguration.getNodes()){
                d.getPuppetModuleInstances().add(instance);
            }
        }
    }

    public void sendFileToRole(PlatformExecution plaformConfiguration,RolExecution roleConfiguration,File f, String path) {
        SCP[] copias = new SCP[roleConfiguration.getNodes().size()];
        for (int e = 0; e < copias.length; e++) {
            copias[e] = new SCP(f, roleConfiguration.getNodes().get(e), path, new RuntimeExecutionLog(plaformConfiguration.getId(), "node:" + roleConfiguration.getNodes().get(e).getHostname()));
        }
        for (SCP copia : copias) {
            copia.waitFor();
        }
    }

    public void executeCommandOnRole(PlatformExecution plaformConfiguration,RolExecution roleConfiguration,String command){
        SSHCommand[] copias = new SSHCommand[roleConfiguration.getNodes().size()];
        for (int e = 0; e < copias.length; e++) {
            copias[e] = new SSHCommand(roleConfiguration.getNodes().get(e), command, new RuntimeExecutionLog(plaformConfiguration.getId(), "node:" + roleConfiguration.getNodes().get(e).getHostname()));
        }
        for (SSHCommand copia : copias) {
            if (copia != null) {
                copia.waitFor();
            }
        }
    }
    public SSHCommandNoWait[] executeAsynchronusCommandOnRole(PlatformExecution plaformConfiguration,RolExecution roleConfiguration,String command, int processSize){
        SSHCommandNoWait[] copias = new SSHCommandNoWait[roleConfiguration.getNodes().size()];
        for (int e = 0; e < copias.length; e++) {
            copias[e] = new SSHCommandNoWait(roleConfiguration.getNodes().get(e), command, new RuntimeExecutionLog(plaformConfiguration.getId(), "node:" + roleConfiguration.getNodes().get(e).getHostname()),1);
        }
        for (int e = 0; e < copias.length; e++) {
            if (copias[e] != null) {
                copias[e].waitFor();
            }
        }
        return copias;
    }

    public static void executeCommandOnRole(PlatformExecution plaformConfiguration,RolExecution roleConfiguration,String command, long timeWait) {
        SSHCommand[] copias = new SSHCommand[roleConfiguration.getNodes().size()];
        for (int e = 0; e < copias.length; e++) {
            copias[e] = new SSHCommand(roleConfiguration.getNodes().get(e), command, new RuntimeExecutionLog(plaformConfiguration.getId(), "node:" + roleConfiguration.getNodes().get(e).getHostname()));
            PaaSUtils.sleep(timeWait);
        }
        for (SSHCommand copia : copias) {
            if (copia != null) {
                copia.waitFor();
            }
        }
    }

    public void executeCommands(PlatformExecution plaformConfiguration,RolExecution roleConfiguration) {
        System.out.println("Not empty "+roleConfiguration.getCommand().isEmpty());
        for (Command command : roleConfiguration.getCommand()) {
            StringBuilder script = new StringBuilder("#!/bin/bash\n");
            String path;
            if (command.getType() == ResourceType.GLOBAL) {
                path = "/unacloud/cluster";
            } else {
                path = "/unacloud/node";
            }
            script.append("cd ");
            script.append(path);
            script.append("\n");
            script.append(Parser.parseFromEnvironment(command.getCommand(),roleConfiguration,plaformConfiguration));
            script.append(" > uc");
            script.append(command.getId());
            script.append(".out");
            script.append(" 2> uc");
            script.append(command.getId());
            script.append(".err");
            script.append("\n");

            try (PrintWriter pw = new PrintWriter(new File(FolderManager.getPlatformFolder(plaformConfiguration.getId()), "unacloudExe.sh"))) {
                System.out.println("Imprimió archivo");
                pw.println(script);
            } catch (Exception ex) {
                Logger.getLogger(RuntimeRoleExecutionBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("command user: "+command.getRunningUser());
            if (command.getRunningUser() != null) {
                try (PrintWriter pw = new PrintWriter(new File(FolderManager.getPlatformFolder(plaformConfiguration.getId()), "unacloudUser.sh"))) {
                    pw.println("su -l " + command.getRunningUser() + " < /unacloud/cluster/unacloudExe.sh");
                } catch (Exception ex) {
                    Logger.getLogger(RuntimeRoleExecutionBean.class.getName()).log(Level.SEVERE, null, ex);
                }
                try (PrintWriter pw = new PrintWriter(new File(FolderManager.getPlatformFolder(plaformConfiguration.getId()), "unacloudStart.sh"))) {
                    pw.println("nohup sh /unacloud/cluster/unacloudUser.sh &");
                    pw.println("echo $!");
                } catch (Exception ex) {
                    Logger.getLogger(RuntimeRoleExecutionBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try (PrintWriter pw = new PrintWriter(new File(FolderManager.getPlatformFolder(plaformConfiguration.getId()), "unacloudStart.sh"))) {
                    pw.println("nohup sh /unacloud/cluster/unacloudExe.sh &");
                    pw.println("echo $!");
                } catch (Exception ex) {
                    Logger.getLogger(RuntimeRoleExecutionBean.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            System.out.println("waitForFile");
            System.out.println("Multiplicity "+command.getMultiplicity());
            if (command.getMultiplicity() == MultiplicityType.ONE) {
                Node d = roleConfiguration.getNodes().get(new Random().nextInt(roleConfiguration.getNodes().size()));
                SSHCommandNoWait commandWait = new SSHCommandNoWait(d, "sh " + path + "/unacloudStart.sh", new RuntimeExecutionLog(plaformConfiguration.getId(), "CommandRunner:" + roleConfiguration.getRol().getName()), 1);
                commandWait.waitFor();
                for (Long l : commandWait.getProcessIds()) {
                    platformExecutionManagerBean.addCommandWait(d.getId(), l);
                }
            }else if (command.getMultiplicity() == MultiplicityType.MANY) {
                System.out.println("Execuring....");
                SSHCommandNoWait[] processes = executeAsynchronusCommandOnRole(plaformConfiguration,roleConfiguration,"sh " + path + "/unacloudStart.sh", 1);
                for(SSHCommandNoWait process:processes){
                    for (Long l : process.getProcessIds()) {
                        platformExecutionManagerBean.addCommandWait(process.getDest().getId(), l);
                    }
                }
            }
        }
    }
}