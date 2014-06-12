package unacloud.paas.data.execution;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ga.sotelo69 on 23/05/2014.
 */
public class RolDescription {
    public long id;
    public int size;
    /**
     * Cores per VM
     */
    public int cores;
    public List<PuppetModule> modules=new ArrayList<>();

    public RolDescription(long id, int size, int cores) {
        this.id = id;
        this.size = size;
        this.cores = cores;
    }
    public static class PuppetModule{
        public long id;
        public PuppetModule(long id) {
            this.id = id;
        }
        public List<PuppetParam> roles=new ArrayList<>();
    }
    public static class PuppetParam{

    }
}
