/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unacloud.paas.back.beans;

import com.avaje.ebean.Ebean;
import models.User;
import unacloud.paas.back.user.FolderManager;

import java.util.List;

public class UserManagerBean {

    public static List<User> getUserList() {
        return Ebean.find(User.class).findList();
    }
    public static void registerUser(User user){
        Ebean.save(user);
        //entityManager.persist(user);
        FolderManager.createUserFolder(user.getUsername());
    }
}
