package unacloud.paas.data.execution;

import models.PlatformExecution;
import models.enums.ResourceType;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;

import static unacloud.paas.back.user.FolderManager.PLATFORMS_FOLDER;

public class FileDescriptionEntity implements Serializable{
   String name,path;
   transient InputStream content;
   ResourceType fileType;
   String assignedRole;
   boolean unzip;
   public FileDescriptionEntity(){
   }
   public FileDescriptionEntity(String name, String path){
      this.name=name;
      this.path=path;
   }
   public FileDescriptionEntity(String name, String path, InputStream content, ResourceType fileType, String assignedRole){
      this.name=name;
      this.path=path;
      this.content=content;
      this.fileType=fileType;
      this.assignedRole=assignedRole;
   }
   public String getName(){
      return name;
   }
   public void setName(String name){
      this.name=name;
   }
   public String getPath(){
      return path;
   }
   public void setPath(String path){
      this.path=path;
   }
   public InputStream getContent(){
      return content;
   }
   public void setContent(InputStream content){
      this.content=content;
   }
   public ResourceType getFileType(){
      return fileType;
   }
   public void setFileType(ResourceType fileType){
      this.fileType=fileType;
   }
   public String getAssignedRole(){
      return assignedRole;
   }
   public void setAssignedRole(String assignedRole){
      this.assignedRole=assignedRole;
   }
   public boolean isGlobal(){
      return fileType==ResourceType.Global;
   }
   public boolean isUnzip(){
      return unzip;
   }
   public File getPathRespectToExecution(PlatformExecution platformExecution){
       String p=(path.startsWith("/")?path.substring(1):path);
       p+=p.endsWith("/")?"":"/";
       return new File(new File(PLATFORMS_FOLDER, platformExecution.getHexId()), p+name);
   }
   public void setUnzip(boolean unzip){
      this.unzip=unzip;
   }
    @Override
    public String toString() {
        return "FileDescriptionEntity{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", content=" + content +
                ", fileType=" + fileType +
                ", assignedRole='" + assignedRole + '\'' +
                ", unzip=" + unzip +
                '}';
    }
}