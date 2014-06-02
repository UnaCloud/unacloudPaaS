package unacloud.paas.back.beans;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;
import models.PuppetModule;

import java.util.List;

public class PuppetModuleManagerBean {

    public static void insertPuppetModule(PuppetModule module) {
        Ebean.save(module);
    }

    public static void updatePuppetModule(PuppetModule module) {
        Ebean.update(module);
    }

    public static List<PuppetModule> getPuppetModules() {
        return Ebean.find(PuppetModule.class).findList();
        //return entityManager.createNamedQuery("Puppetmodule.findAll", PuppetModule.class).getResultList();
    }

    public static List<PuppetModule> getPuppetModules(String query) {
        return Ebean.find(PuppetModule.class).where(Expr.like("name",query)).findList();
        /*TypedQuery<PuppetModule> typedQuery = entityManager.createNamedQuery("Puppetmodule.findByNameLike", PuppetModule.class);
        typedQuery.setParameter("name", "%" + query + "%");
        return typedQuery.getResultList();*/
    }

    public static PuppetModule getPuppetModule(String name) {
        return Ebean.find(PuppetModule.class).where(Expr.eq("name",name)).findUnique();
        /*TypedQuery<PuppetModule> typedQuery = entityManager.createNamedQuery("Puppetmodule.findByName", PuppetModule.class);
        typedQuery.setParameter("name", name);
        return typedQuery.getSingleResult();*/
    }
}
