package unacloud.paas.back.beans;

import com.avaje.ebean.Ebean;
import models.Platform;
import models.Rol;

import java.util.List;

public class PlatformManagerBean {

    public static Platform getFullPlatform(long plaformId) {
        return Platform.find.byId(plaformId);
    }

    public static List<Platform> getList() {
        return Ebean.find(Platform.class).findList();
    }

    public static Rol getRole(String roleName, int platformId) {
        Rol role = new Rol();
        role.name=roleName;
        return role;
    }
}
