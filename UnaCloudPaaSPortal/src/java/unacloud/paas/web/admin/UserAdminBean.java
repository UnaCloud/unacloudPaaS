/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unacloud.paas.web.admin;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import unacloud.paas.data.entities.User;
import unacloud.paas.data.managers.UserManager;
/**
 *
 * @author G
 */
@ManagedBean
@RequestScoped
public class UserAdminBean{
    List<User> userList;
    public UserAdminBean(){
    }
    public List<User> getUserList(){
        if(userList==null){
            userList=UserManager.getUserList();
        }
        return userList;
    }
    public void setUserList(List<User> userList){
        this.userList=userList;
    }
}