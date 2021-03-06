package unacloud.paas.back.puppet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
public class PuppetModuleInstance{

    String moduleName;
    Map<String, String> variables=new TreeMap<>();

    public PuppetModuleInstance(){
    }
    public PuppetModuleInstance(String moduleName){
        this.moduleName=moduleName;
    }
    public String getModuleName(){
        return moduleName;
    }
    public void setModuleName(String moduleName){
        this.moduleName=moduleName;
    }
    public PuppetModuleInstance addValue(String key, String value){
        variables.put(key, value);
        return this;
    }
    @Override
    public String toString(){
        String h="class {'"+getModuleName()+"':";
        for(Entry<String, String> ent : variables.entrySet())
            h+="\n\t"+ent.getKey()+" => "+ent.getValue()+",";
        return h+"}";
    }
    public String toPuppetString() {
            String h= "\tpackage { '"+getModuleName()+"' :\n";
            h+="\t\tensure\t=> installed,\n";
            h+="\t}";
            return h;
    }
}
