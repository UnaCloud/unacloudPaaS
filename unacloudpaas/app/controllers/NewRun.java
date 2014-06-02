package controllers;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.MainCommand;
import models.Platform;
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

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static play.data.Form.*;

public class NewRun extends Controller {

    public static Result index() {
        return ok(newRun.render(Ebean.find(Platform.class).findList()));
    }

    public static Result newRun(Long platformId) {
        if(platformId==null)return badRequest("Error");
        Platform plat=Ebean.find(Platform.class,platformId);
        MainCommand c=Ebean.find(MainCommand.class,plat.mainCommand.id);
        if(plat==null)return badRequest("Error");
        return ok(newRunConfig.render(plat));
    }
    public static Result runningList() {
        return ok(running.render());
    }

    public static Result runningHistory() {
        return ok(runningHistory.render());
    }

    public static Result start(){
        long userId=1;
        Map<String,String> data=Form.form().bindFromRequest().data();
        for(Map.Entry<String,String> e:data.entrySet()){
            System.out.println(" "+e.getKey()+" "+e.getValue());
        }
        long platformId=parseLong(data.get("platformId"));
        String executionName=data.get("executionName");

        String commandArgs=data.get("commandArgs"),commandType=data.get("commandType");

        int roles=parseInt(data.get("roles"));
        RolDescription[] roleDescriptions=new RolDescription[roles];
        for(int e=0;e<roles;e++){
            roleDescriptions[e]=new RolDescription();
            roleDescriptions[e].id=parseLong(data.get("rolId"+e));
            roleDescriptions[e].cores=parseInt(data.get("cores"+e));
            roleDescriptions[e].size=parseInt(data.get("size"+e));
        }

        List<Http.MultipartFormData.FilePart> filesData=request().body().asMultipartFormData().getFiles();
        List<FileDescriptionEntity> files=new ArrayList<>();
        for(int e=0;e<files.size();e++){
            FileDescriptionEntity fde = new FileDescriptionEntity();
            try{
                fde.setContent(new FileInputStream(filesData.get(e).getFile()));
            }catch (IOException ex){}
            fde.setFileType(ResourceType.valueOf(data.get("fileType" + e)));
            fde.setPath(data.get("filePath" + e));
            fde.setName(filesData.get(e).getFilename());
            fde.setUnzip(false);
        }
        System.out.println("createExecution");
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
