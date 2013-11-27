/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unacloud.paas.web.ws.webservices;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import unacloud.paas.back.cluster.platforms.ClusterManager;
import unacloud.paas.data.entities.MainCommand;
import unacloud.paas.data.entities.PuppetModuleUsage;
import unacloud.paas.data.entities.enums.ResourceType;
import unacloud.paas.data.execution.CommandExecutionEntity;
import unacloud.paas.data.execution.FileDescriptionEntity;
import unacloud.paas.data.execution.PlatformExecutionEntity;
import unacloud.paas.data.execution.RoleExecutionEntity;
import unacloud.paas.data.managers.PlatformExecutionManager;
import unacloud.paas.data.managers.PuppetModuleManager;
import unacloud.paas.web.webservices.PaaSAPIWS;

/**
 *
 * @author Clouder
 */
@WebService(serviceName = "PaaSWebService")
public class PaaSWebService {
    public static enum RUN_TYPE{
        ALONE,DUPLEX
    } 
    public static enum PLATFORM{
        HPL,IOZONE
    } 
    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "runTest")
    public String runTest(@WebParam(name = "test") PLATFORM test,@WebParam(name = "cores") int cores,@WebParam(name = "runType") RUN_TYPE type,@WebParam(name = "specialId") int specialId) {
        PlatformExecutionEntity toRun=PlatformExecutionManager.generateVoidPlatformExecution(test== PLATFORM.HPL?1:3);
        List<FileDescriptionEntity> files=new ArrayList<>();
        toRun.getRoles().get(0).setCores(cores);
        toRun.getRoles().get(0).setCoresPerNode(1);
        String mainCommandArgs/*=""*/;
        if(test== PLATFORM.HPL){
            toRun.setRunName("CCGridHPL_"+cores+(type== RUN_TYPE.DUPLEX?":"+specialId:""));
            try {
                FileDescriptionEntity hpl=new FileDescriptionEntity("HPL.dat","/",new FileInputStream("/HPL"+cores+".dat"), ResourceType.GLOBAL, toRun.getPlatform().getRoles().get(0).getRoleName());
                files.add(hpl);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(PaaSAPIWS.class.getName()).log(Level.SEVERE, null, ex);
            }
            toRun.getRoles().get(0).getPuppetModules().add(new PuppetModuleUsage(-1, PuppetModuleManager.getPuppetModule("hpl")));
            mainCommandArgs="xhpl";
        }else if(test== PLATFORM.IOZONE){
            toRun.setRunName("CCGridIozone_"+cores+(type== RUN_TYPE.DUPLEX?":"+specialId:""));
            try {
                FileDescriptionEntity hpl=new FileDescriptionEntity("runIozone.sh","/",new FileInputStream("/runIozone.sh"), ResourceType.GLOBAL, toRun.getPlatform().getRoles().get(0).getRoleName());
                files.add(hpl);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(PaaSAPIWS.class.getName()).log(Level.SEVERE, null, ex);
            }
            toRun.getRoles().get(0).getPuppetModules().add(new PuppetModuleUsage(-1, PuppetModuleManager.getPuppetModule("iozone")));
            mainCommandArgs="runIozone.sh";
        }else return "NotValid platformId";
        MainCommand mainCommand=toRun.getPlatform().getMainCommand();
        if(mainCommand!=null){
           for(RoleExecutionEntity role:toRun.getRoles())if(role.getRoleConfig().getRoleName().equals(mainCommand.getRoleId())){
              for(CommandExecutionEntity command:role.getCommandExecutions())if(command.isMainCommand()){
                 command.setCommand(mainCommand.getCommand().trim()+" "+mainCommandArgs.trim());
              }
           }
        }
        try{
            ClusterManager.createCluster(toRun,"gasotelo", files,type==RUN_TYPE.DUPLEX);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return ""+toRun.getId()+" "+toRun.getHexId();
    }
}
