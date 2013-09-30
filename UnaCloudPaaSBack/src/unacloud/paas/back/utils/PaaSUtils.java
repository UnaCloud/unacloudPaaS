package unacloud.paas.back.utils;
import java.io.BufferedReader;
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
    
    private static int contador=0;
    private final static Object sincronizador = new Object();
    public static void waitForFile(){
       /*synchronized(sincronizador){
          if(contador==1){
             contador=0;
             sincronizador.notifyAll();
          }else{
             contador++;
             try{
                sincronizador.wait();
             }catch(InterruptedException ex){
                Logger.getLogger(PaaSUtils.class.getName()).log(Level.SEVERE, null, ex);
             }
          }
       }*/
    }
}
