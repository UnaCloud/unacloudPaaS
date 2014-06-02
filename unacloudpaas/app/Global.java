import com.avaje.ebean.*;
import models.*;
import models.enums.MultiplicityType;
import models.enums.ResourceType;
import play.*;
import play.libs.*;

import java.util.List;
import java.util.Map;

public class Global extends GlobalSettings {
    
    public void onStart(Application app) {
        InitialData.insert(app);
    }
    
    static class InitialData {
        
        public static void insert(Application app) {
            if(Ebean.find(User.class).findRowCount() == 0) {
                PuppetModule moduleIozone=new PuppetModule("Iozone","Data input/ouput benchmark");
                PuppetModule moduleHpl=new PuppetModule("hpl","Install hpl.");
                PuppetModule moduleCondor=new PuppetModule("condor","install condor");
                moduleCondor.puppetParams.add(new PuppetParam("master","$(FULL_HOSTNAME)"));
                moduleCondor.puppetParams.add(new PuppetParam("daemonlist","'MASTER, COLLECTOR, NEGOTIATOR, SCHEDD'"));
                PuppetModule moduleMpi=new PuppetModule("openmpi","Installs the openmpi-bin and openmpi-common packages");
                PuppetModule moduleJava7=new PuppetModule("java7","Install sun\\'s jdk java 7");
                PuppetModule moduleBoToBlas2=new PuppetModule("gotoblas2","Installs gotoblas2 into /unacloud/gotoblas");
                PuppetModule moduleGromacs=new PuppetModule("gromacs","Install the openmpi-dev and gromacs 4.5 packages");

                Ebean.save(moduleIozone);
                Ebean.save(moduleHpl);
                Ebean.save(moduleCondor);
                Ebean.save(moduleMpi);
                Ebean.save(moduleJava7);
                Ebean.save(moduleBoToBlas2);
                Ebean.save(moduleGromacs);

                Platform pShell =   new Platform(33,"Generic shell","unacloud.paas.waiters.CommandWaiter");
                Platform pCondor =  new Platform(33,"Condor","unacloud.paas.waiters.CondorWaiter");
                Platform pMpi =     new Platform(33,"MPI","unacloud.paas.waiters.CommandWaiter");

                Rol shellExecRole=new Rol(1,8,false, MultiplicityType.MANY, "slave");
                pShell.roles.add(shellExecRole);
                Rol mpiExecRole=new Rol(2,8,true, MultiplicityType.MANY, "exec");
                pMpi.roles.add(mpiExecRole);
                Rol condorMasterRole=new Rol(3,8,true, MultiplicityType.ONE, "condormaster");
                pCondor.roles.add(condorMasterRole);
                Rol condorSlaveRole=new Rol(4,8,false, MultiplicityType.MANY, "condorslave");
                pCondor.roles.add(condorSlaveRole);

                {
                    PuppetModuleUsage pmuCondorMaster=new PuppetModuleUsage(moduleCondor);
                    pmuCondorMaster.puppetParamValue.add(new PuppetParamValue("daemonlist","'MASTER, COLLECTOR, NEGOTIATOR, SCHEDD'"));
                    pmuCondorMaster.puppetParamValue.add(new PuppetParamValue("master","$(FULL_HOSTNAME)"));
                    condorMasterRole.puppetModule=pmuCondorMaster;

                    PuppetModuleUsage pmuCondorSlave=new PuppetModuleUsage(moduleCondor);
                    pmuCondorSlave.puppetParamValue.add(new PuppetParamValue("daemonlist","'MASTER, STARTD'"));
                    pmuCondorSlave.puppetParamValue.add(new PuppetParamValue("master","$condormaster.hostname"));
                    condorSlaveRole.puppetModule=pmuCondorSlave;

                    PuppetModuleUsage pmuMpi=new PuppetModuleUsage(moduleMpi);
                    mpiExecRole.puppetModule=pmuMpi;
                }

                pMpi.mainCommand=new MainCommand("mpirun -n $exec.cores -hostfile machinesFile",MultiplicityType.ONE, ResourceType.LOCAL,null,mpiExecRole.id);
                pCondor.mainCommand=new MainCommand("condor_submit",MultiplicityType.ONE, ResourceType.LOCAL,"daemon",condorMasterRole.id);
                pShell.mainCommand=new MainCommand("sh",MultiplicityType.MANY, ResourceType.LOCAL,"daemon",shellExecRole.id);


                System.out.println("El comando es "+pShell.mainCommand.command);
                Ebean.save(pShell);
                Ebean.save(pCondor);
                Ebean.save(pMpi);

                @SuppressWarnings("unchecked")
				Map<String,List<Object>> all = (Map<String,List<Object>>)Yaml.load("initial-data.yml");

                // Insert users first
                Ebean.save(all.get("users"));

            }
        }
        
    }
    
}