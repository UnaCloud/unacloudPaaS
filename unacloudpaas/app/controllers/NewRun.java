package controllers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.MainCommand;
import models.Platform;
import models.PlatformExecution;
import models.enums.ExecutionState;
import models.enums.ResourceType;
import play.*;
import play.libs.Json;
import play.data.*;
import play.data.validation.Constraints;
import play.data.validation.Constraints.*;
import play.mvc.*;
import unacloud.paas.back.beans.ClusterManagerBean;
import unacloud.paas.data.execution.FileDescriptionEntity;
import unacloud.paas.data.execution.RolDescription;
import views.html.*;

import java.beans.Expression;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import static play.data.Form.*;

public class NewRun extends Controller {

    public static Result index() {
        return ok(newRun.render(Ebean.find(Platform.class).findList()));
    }

    @Security.Authenticated(Secured.class)
    public static Result newRun(Long platformId) {
        if(platformId==null)return badRequest("Error");
        Platform plat=Ebean.find(Platform.class,platformId);
        MainCommand c=Ebean.find(MainCommand.class,plat.mainCommand.id);
        if(plat==null)return badRequest("Error");
        return ok(newRunConfig.render(plat));
    }
    public static int runningCount(){
        return PlatformExecution.find.where(Expr.in("status",new ExecutionState[]{ExecutionState.CREATING,ExecutionState.RUNNING})).findRowCount();
    }
    public static Result runningList() {
        return ok(running.render());
    }

    public static Result start(){
        long userId=1;
        Map<String,String> data=Form.form().bindFromRequest().data();
        TreeMap tData=new TreeMap<String,String>(data);

        long platformId=parseLong(data.get("platformId"));
        String executionName=data.get("executionName");

        String commandArgs=data.get("commandArgs"),commandType=data.get("commandType");

        int roles=parseInt(data.get("roles"));
        RolDescription[] roleDescriptions=new RolDescription[roles];
        for(int e=0;e<roles;e++){
            long rolId=parseLong(data.get("rolId"+e));
            roleDescriptions[e]=new RolDescription(rolId,parseInt(data.get("cores"+e)),parseInt(data.get("size"+e)));
            SortedMap<String,String> modules=tData.subMap("rolModule_"+rolId+"_",true,"rolModule_"+(rolId+1)+"_",false);
            for(Map.Entry<String,String> mod:modules.entrySet()){
                roleDescriptions[e].modules.add(new RolDescription.PuppetModule((Long.parseLong(mod.getValue()))));
            }
        }

        List<Http.MultipartFormData.FilePart> filesData=request().body().asMultipartFormData().getFiles();
        List<FileDescriptionEntity> files=new ArrayList<>();
        for(int e=0;e<filesData.size();e++){
            FileDescriptionEntity fde = new FileDescriptionEntity(filesData.get(e).getFilename(),data.get("filePath" + e));
            try{
                fde.setContent(new FileInputStream(filesData.get(e).getFile()));
            }catch (IOException ex){}
            fde.setFileType(ResourceType.valueOf(data.get("fileType" + e)));
            fde.setUnzip(fde.getName().endsWith(".zip")&&data.containsKey("fileUnzip"+e)&&data.get("fileUnzip"+e).equals("on"));
            files.add(fde);
        }
        ClusterManagerBean.createExecution(userId, platformId, executionName, commandArgs, roleDescriptions, files);
        return ok(running.render());
    }
    private static int parseInt(String h){
        if(h==null||!h.matches("[0-9]+"))return -1;
        return Integer.parseInt(h);
    }
    private static long parseLong(String h){
        if(h==null||!h.matches("[0-9]+"))return -1;
        return Long.parseLong(h);
    }
}
