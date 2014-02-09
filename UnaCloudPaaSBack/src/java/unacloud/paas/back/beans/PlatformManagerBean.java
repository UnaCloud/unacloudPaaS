package unacloud.paas.back.beans;

import java.sql.Connection;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import unacloud.paas.back.database.entities.Platform;
import unacloud.paas.back.database.entities.Rol;

/**
 *
 * @author G
 */
@Stateless
@LocalBean
public class PlatformManagerBean {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext()
    private EntityManager entityManager;

    public Platform getFullPlatform(int plaformId) {
        return entityManager.find(Platform.class, plaformId);
    }

    public List<Platform> getList() {
        return entityManager.createNamedQuery("Platform.findAll", Platform.class).getResultList();
    }

    public static Rol getRole(String roleName, int platformId, Connection con) {
        Rol role = new Rol();
        role.setName(roleName);
        return role;
    }
}
