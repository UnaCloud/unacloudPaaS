/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unacloud.paas.back.beans;

import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import unacloud.paas.back.database.entities.User;
import unacloud.paas.back.user.FolderManager;

/**
 *
 * @author G
 */
@Stateless
@LocalBean
public class UserManagerBean {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext()
    private EntityManager entityManager;
    public List<User> getUserList() {
        return entityManager.createNamedQuery("User.findAll",User.class).getResultList();
    }
    public void registerUser(User user){
        entityManager.persist(user);
        FolderManager.createUserFolder(user.getUsername());
    }
}
