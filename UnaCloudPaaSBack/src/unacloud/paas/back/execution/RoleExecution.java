/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unacloud.paas.back.execution;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import unacloud.iaas.ws.production.VirtualMachineExecutionWS;
import unacloud.paas.back.iaasservices.ClusterServices;
import unacloud.paas.back.dsl.Parser;
import unacloud.paas.back.sshutils.SCP;
import unacloud.paas.back.sshutils.SSHCommand;
import unacloud.paas.back.sshutils.SSHCommandNoWait;
import unacloud.paas.back.user.FolderManager;
import unacloud.paas.back.utils.PaaSUtils;
import unacloud.paas.data.entities.PuppetModuleUsage;
import unacloud.paas.data.entities.enums.MultiplicityType;
import unacloud.paas.data.entities.enums.ResourceType;
import unacloud.paas.data.execution.CommandExecutionEntity;
import unacloud.paas.data.execution.RoleExecutionEntity;
import unacloud.paas.data.managers.PlatformExecutionManager;

/**
 *
 * @author G
 */
public class RoleExecution {

    private RoleExecutionEntity roleConfiguration;
    final PlatformExecution platformExecution;
    long platformExecutionId;
    public final List<NodeExecution> nodes = new ArrayList<>();
    Thread t = null;

    public RoleExecution(RoleExecutionEntity roleConfiguration, long platformExecutionId, PlatformExecution platformExecution) {
        this.roleConfiguration = roleConfiguration;
        this.platformExecutionId = platformExecutionId;
        this.platformExecution = platformExecution;
    }
    static int pos = 1;

    public void startRole() {
        Logger.getLogger("PaaS").log(Level.INFO, "StartingRole");
        int size = (roleConfiguration.getCores() - 1) / roleConfiguration.getCoresPerNode() + 1;
        //List<VirtualMachineExecutionWS> vms=ClusterServices.startCluster(roleConfiguration.getRoleConfig().getTemplateId(), size, roleConfiguration.getCoresPerNode());
        //List<VirtualMachineExecutionWS> vms = ClusterServices.startCluster((pos++ % 2) == 0 ? 70 : 64, size, roleConfiguration.getCoresPerNode());
        //VBox
        List<VirtualMachineExecutionWS> vms=ClusterServices.startCluster(69/*roleConfiguration.getRoleConfig().getTemplateId()*/, size, roleConfiguration.getCoresPerNode());
        Logger.getLogger("PaaS").log(Level.INFO, "Machines Started");
        for (VirtualMachineExecutionWS vme : vms) {
            nodes.add(new NodeExecution(vme.getVirtualMachineName(), vme.getVirtualMachineExecutionIP(), vme.getVirtualMachineExecutionCode()));
        }
        Logger.getLogger("PaaS").log(Level.INFO, "RoleStarted");
    }

    public void configureRole() {
        for (NodeExecution d : nodes) {
            d.getModules().add(new PuppetModuleInstance("nfsunacloud").addValue("path", "'codd.sis.virtual.uniandes.edu.co:/unacloud/" + Long.toHexString(platformExecutionId) + "'"));
        }
        if (roleConfiguration.getRoleConfig().getPuppetModule() != null) {
            PuppetModuleInstance instance = roleConfiguration.getRoleConfig().getPuppetModule().getpuppetModuleInstance(this, platformExecution);
            for (NodeExecution d : nodes) {
                d.getModules().add(instance);
            }
        }
        for (PuppetModuleUsage pmu : roleConfiguration.getPuppetModules()) {
            PuppetModuleInstance instance = pmu.getpuppetModuleInstance(this, platformExecution);
            for (NodeExecution d : nodes) {
                d.getModules().add(instance);
            }
        }
    }

    public void sendFileToRole(File f, String path) {
        SCP[] copias = new SCP[nodes.size()];
        for (int e = 0; e < copias.length; e++) {
            copias[e] = new SCP(f, nodes.get(e), path, new ExecutionLog(platformExecutionId, "node:" + nodes.get(e).getHostname()));
        }
        for (int e = 0; e < copias.length; e++) {
            copias[e].waitFor();
        }
    }

    public void executeCommandOnRole(String command) {
        SSHCommand[] copias = new SSHCommand[nodes.size()];
        for (int e = 0; e < copias.length; e++) {
            copias[e] = new SSHCommand(nodes.get(e), command, new ExecutionLog(platformExecutionId, "node:" + nodes.get(e).getHostname()));
        }
        for (int e = 0; e < copias.length; e++) {
            if (copias[e] != null) {
                copias[e].waitFor();
            }
        }
    }
    public SSHCommandNoWait[] executeAsynchronusCommandOnRole(String command, int processSize){
        SSHCommandNoWait[] copias = new SSHCommandNoWait[nodes.size()];
        for (int e = 0; e < copias.length; e++) {
            copias[e] = new SSHCommandNoWait(nodes.get(e), command, new ExecutionLog(platformExecutionId, "node:" + nodes.get(e).getHostname()),1);
        }
        for (int e = 0; e < copias.length; e++) {
            if (copias[e] != null) {
                copias[e].waitFor();
            }
        }
        return copias;
    }

    public void executeCommandOnRole(String command, long timeWait) {
        SSHCommand[] copias = new SSHCommand[nodes.size()];
        for (int e = 0; e < copias.length; e++) {
            copias[e] = new SSHCommand(nodes.get(e), command, new ExecutionLog(platformExecutionId, "node:" + nodes.get(e).getHostname()));
            PaaSUtils.sleep(timeWait);
        }
        for (int e = 0; e < copias.length; e++) {
            if (copias[e] != null) {
                copias[e].waitFor();
            }
        }
    }

    public void waitForRole() {
        try {
            t.join();
            t = null;
            ClusterServices.waitForSSH(nodes);
        } catch (InterruptedException ex) {
        }
    }

    public void executeCommands(final boolean WaitParallel) {
        System.out.println("Not empty "+roleConfiguration.getCommandExecutions().isEmpty());
        for (CommandExecutionEntity command : roleConfiguration.getCommandExecutions()) {
            StringBuilder script = new StringBuilder("#!/bin/bash\n");
            String path;
            if (command.getResourceType() == ResourceType.GLOBAL) {
                path = "/unacloud/cluster";
            } else {
                path = "/unacloud/node";
            }
            script.append("cd ");
            script.append(path);
            script.append("\n");
            script.append(Parser.parseFromEnvironment(command.getCommand(), this, platformExecution));
            script.append(" > uc");
            script.append(command.getId());
            script.append(".out");
            script.append(" 2> uc");
            script.append(command.getId());
            script.append(".err");
            script.append("\n");

            try (PrintWriter pw = new PrintWriter(new File(FolderManager.getPlatformFolder(platformExecutionId), "unacloudExe.sh"))) {
                System.out.println("Imprimió archivo");
                pw.println(script);
            } catch (Exception ex) {
                Logger.getLogger(RoleExecution.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("command user: "+command.getUser());
            if (command.getUser() != null) {
                try (PrintWriter pw = new PrintWriter(new File(FolderManager.getPlatformFolder(platformExecutionId), "unacloudUser.sh"))) {
                    pw.println("su -l " + command.getUser() + " < /unacloud/cluster/unacloudExe.sh");
                } catch (Exception ex) {
                    Logger.getLogger(RoleExecution.class.getName()).log(Level.SEVERE, null, ex);
                }
                try (PrintWriter pw = new PrintWriter(new File(FolderManager.getPlatformFolder(platformExecutionId), "unacloudStart.sh"))) {
                    pw.println("nohup sh /unacloud/cluster/unacloudUser.sh &");
                    pw.println("echo $!");
                } catch (Exception ex) {
                    Logger.getLogger(RoleExecution.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try (PrintWriter pw = new PrintWriter(new File(FolderManager.getPlatformFolder(platformExecutionId), "unacloudStart.sh"))) {
                    pw.println("nohup sh /unacloud/cluster/unacloudExe.sh &");
                    pw.println("echo $!");
                } catch (Exception ex) {
                    Logger.getLogger(RoleExecution.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            System.out.println("waitForFile");
            if(WaitParallel)PaaSUtils.waitForFile();
            System.out.println("Multiplicity "+command.getMultiplicity());
            if (command.getMultiplicity() == MultiplicityType.ONE) {
                NodeExecution d = getNodes().get(new Random().nextInt(getNodes().size()));
                SSHCommandNoWait commandWait = new SSHCommandNoWait(d, "sh " + path + "/unacloudStart.sh", new ExecutionLog(platformExecutionId, "CommandRunner:" + getRoleName()), 1);
                commandWait.waitFor();
                for (Long l : commandWait.getProcessIds()) {
                    PlatformExecutionManager.addCommandWait(d.getId(), l);
                }
            }else if (command.getMultiplicity() == MultiplicityType.MANY) {
                System.out.println("Execuring....");
                SSHCommandNoWait[] processes = executeAsynchronusCommandOnRole("sh " + path + "/unacloudStart.sh", 1);
                for(SSHCommandNoWait process:processes){
                    for (Long l : process.getProcessIds()) {
                        PlatformExecutionManager.addCommandWait(process.getDest().getId(), l);
                    }
                }
            }
        }
    }

    public List<NodeExecution> getNodes() {
        return nodes;
    }

    public String getRoleName() {
        return roleConfiguration.getRoleConfig().getRoleName();
    }

    public int getTemplate() {
        return roleConfiguration.getRoleConfig().getTemplateId();
    }

    public RoleExecutionEntity getRoleConfiguration() {
        return roleConfiguration;
    }
}