package unacloud.paas.back.utils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PaaSUtils{
    public static void sleep(long l){
        try{
            Thread.sleep(l);
        }catch(InterruptedException e){
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static String[] readRsaId(String filePath){
        try(BufferedReader br=new BufferedReader(new FileReader(filePath))){
            return br.readLine().split(" |\t");
        }catch(Exception e){
        }
        return new String[3];
    }
    public static String getMd5(String chain){
        try{
            MessageDigest message=MessageDigest.getInstance("md5");
            return new BigInteger(1, message.digest(chain.getBytes())).toString(32);
        }catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return "";
    }
    public static void waitForFile(){
        long l=System.currentTimeMillis();
        long f=(l/(60000l)+1)*60000l;
        try {
            while(!new File("/start.txt").exists()){
                Thread.sleep(f-l);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(PaaSUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
