/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unacloud.paas.web.admin;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import unacloud.paas.back.beans.UserManagerBean;
import unacloud.paas.back.database.entities.User;
/**
 *
 * @author G
 */
@ManagedBean
@RequestScoped
public class UserAdminBean{
    @EJB
    private UserManagerBean userManagerBean;
    List<User> userList;
    public UserAdminBean(){
    }
    public List<User> getUserList(){
        if(userList==null){
            userList=userManagerBean.getUserList();
        }
        return userList;
    }
    public void setUserList(List<User> userList){
        this.userList=userList;
    }
}