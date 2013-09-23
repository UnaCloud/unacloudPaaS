/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unacloud.paas.data.entities;

/**
 *
 * @author G
 */
public class PuppetModuleParam {
    String name;
    String defaultValue;
    public PuppetModuleParam() {
    }
    public PuppetModuleParam(String name, String defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDefaultValue() {
        return defaultValue;
    }
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public String toString() {
        return "PuppetModuleParam{" + "name=" + name + ", defaultValue=" + defaultValue + '}';
    }
}
