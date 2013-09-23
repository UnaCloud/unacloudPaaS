/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unacloud.paas.data.managers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import unacloud.paas.back.database.DatabaseConnection;
import unacloud.paas.data.entities.User;

/**
 *
 * @author G
 */
public class UserManager {

    public static List<User> getUserList() {
        ArrayList<User> ret = new ArrayList<>();
        try (Connection con = DatabaseConnection.getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery("select `username`,`firstname`,`lastname`,`hash`,`email`,`description`,activated from `unacloudpaas`.`user`;")) {
            while(rs.next()){
                ret.add(new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(5), rs.getString(6), rs.getString(4), rs.getBoolean(7)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }
}
