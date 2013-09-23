/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unacloud.paas.web.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import unacloud.paas.back.database.DatabaseConnection;
import unacloud.paas.back.user.FolderManager;

/**
 *
 * @author G
 */
@ManagedBean
@RequestScoped
public class UserRegisterBean {

    String username,firstname,lastname,password,repeatpass,email,description;
    public UserRegisterBean() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatpass() {
        return repeatpass;
    }

    public void setRepeatpass(String repeatpass) {
        this.repeatpass = repeatpass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String insert(){
        if(password==null||repeatpass==null||password.isEmpty()||!password.equals(repeatpass))FacesContext.getCurrentInstance().addMessage("valid", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Passwords doesn't match", ""));
        else{
            try {
                Connection con= DatabaseConnection.getConnection();
                PreparedStatement ps=con.prepareStatement("INSERT INTO `unacloudpaas`.`user`(`username`,`firstname`,`lastname`,`password`,`hash`,`email`,`description`)\n" +
                    "VALUES (?,?,?,md5(?),md5(?),?,?);");
                ps.setString(1,username);
                ps.setString(2,firstname);
                ps.setString(3,lastname);
                ps.setString(4,password);
                ps.setString(5,username);
                ps.setString(6,email);
                ps.setString(7,description);
                ps.executeUpdate();
                ps.close();con.close();
                FolderManager.createUserFolder(username);
            } catch (SQLException ex) {
                Logger.getLogger(UserRegisterBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
}

