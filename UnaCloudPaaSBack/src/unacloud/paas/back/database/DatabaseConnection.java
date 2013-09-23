/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unacloud.paas.back.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 *
 * @author G
 */
public class DatabaseConnection {
    private static DataSource ds;
    public static Connection getConnection()throws SQLException{
        return getConnection(true);
    }
    
    public static Connection getConnection(boolean autoComit)throws SQLException{
        try {
            Context c = new InitialContext();
            ds=(DataSource) c.lookup("paasdb");
        } catch (Exception ex) {
        }
        Connection ret;
        if(ds!=null)ret=ds.getConnection();
        else{ try{
           ret=DriverManager.getConnection("jdbc:mysql://157.253.236.162:8080/unacloudpaas","root","mJYGul8jVxaGFSOMllS8");
        }catch(Exception e){}
           ret=DriverManager.getConnection("jdbc:mysql://157.253.236.162:8080/unacloudpaas","unacloud","lgo((ou.[5}LzneIjergr`\"GJ0qtf*K)XwSj!0th");
        }
        ret.setAutoCommit(autoComit);
        return ret;
    }
}
