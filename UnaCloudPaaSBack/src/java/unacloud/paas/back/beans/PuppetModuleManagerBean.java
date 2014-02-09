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
import javax.persistence.TypedQuery;
import unacloud.paas.back.database.entities.PuppetModule;

/**
 *
 * @author G
 */
@Stateless
@LocalBean
public class PuppetModuleManagerBean {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext()
    private EntityManager entityManager;

    public void insertPuppetModule(PuppetModule module) {
        entityManager.persist(module);
    }

    public void updatePuppetModule(PuppetModule module) {
        entityManager.merge(module);
    }

    public List<PuppetModule> getPuppetModules() {
        return entityManager.createNamedQuery("Puppetmodule.findAll", PuppetModule.class).getResultList();
    }

    public List<PuppetModule> getPuppetModules(String query) {
        TypedQuery<PuppetModule> typedQuery = entityManager.createNamedQuery("Puppetmodule.findByNameLike", PuppetModule.class);
        typedQuery.setParameter("name", "%" + query + "%");
        return typedQuery.getResultList();
    }

    public PuppetModule getPuppetModule(String name) {
        TypedQuery<PuppetModule> typedQuery = entityManager.createNamedQuery("Puppetmodule.findByName", PuppetModule.class);
        typedQuery.setParameter("name", name);
        return typedQuery.getSingleResult();
    }
}
