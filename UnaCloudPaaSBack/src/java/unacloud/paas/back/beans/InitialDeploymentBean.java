/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unacloud.paas.back.beans;

import com.google.common.hash.Hashing;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import unacloud.paas.back.database.entities.User;

/**
 *
 * @author G
 */
@Stateless
@LocalBean
public class InitialDeploymentBean {
    @PersistenceContext(unitName = "UnaCloudPaaSBackPU")
    private EntityManager em;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public void doInitialDeployment(){
        em.persist(new User("admin","Admin","Administrador","asdasdasd",Hashing.md5().hashBytes("admin".getBytes()).toString(),"admin@unacloud.com","Nothing"));
    }

}
