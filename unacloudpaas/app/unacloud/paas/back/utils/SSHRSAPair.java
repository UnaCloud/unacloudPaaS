package unacloud.paas.back.utils;

import unacloud.paas.back.sshutils.ProcessManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class SSHRSAPair{
   static Random r=new Random();
   File rsaId,rsaIdPub;
   String keyType;
   String privateKey;
   final long keyId=r.nextLong();
   public SSHRSAPair(){
      if(!new File("generateKeys.sh").exists()){
         try(PrintWriter pw=new PrintWriter("generateKeys.sh")){
            pw.println("#!/bin/bash");
            pw.println("ssh-keygen -t rsa -f $1 -N \"\"");
         }catch(FileNotFoundException ex){
             ex.printStackTrace();
         }
      }
      rsaId=new File("./utils/rsaId"+keyId);
      rsaIdPub=new File("./utils/rsaId"+keyId+".pub");
      new ProcessManager(null, "sh generateKeys.sh "+new File("./utils/rsaId"+keyId).getAbsolutePath()).waitFor();
      String[] generatedId=PaaSUtils.readRsaId("./utils/rsaId"+keyId+".pub");
      keyType=generatedId[0];
      privateKey=generatedId[1];
   }
   public void clear(){
      rsaId.delete();
      rsaIdPub.delete();
   }
   public File getRsaId(){
      return rsaId;
   }
   public String getKeyType(){
      return keyType;
   }
   public String getPrivateKey(){
      return privateKey;
   }
   public long getKeyId(){
      return keyId;
   }
}