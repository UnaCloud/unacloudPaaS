/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unacloud.paas.web.admin;

import com.google.common.hash.Hashing;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import unacloud.paas.back.beans.UserManagerBean;
import unacloud.paas.back.database.entities.User;
import unacloud.paas.back.user.FolderManager;

/**
 *
 * @author G
 */
@ManagedBean
@RequestScoped
public class UserRegisterBean {
    @EJB
    private UserManagerBean userManagerBean;
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
            User user=new User();
            user.setUsername(username);
            user.setFirstname(firstname);
            user.setEmail(email);
            user.setLastname(lastname);
            user.setPassword(password);
            user.setDescription(description);
            user.setActivated(false);
            user.setHash(Hashing.md5().hashBytes(username.getBytes()).toString());
        }
        return null;
    }
}

