/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unacloud.paas.web.admin;

/**
 *
 * @author G
 */
public class PlatformRole {
    String roleName;
    char multiplicity;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public char getMultiplicity() {
        return multiplicity;
    }

    public void setMultiplicity(char multiplicity) {
        this.multiplicity = multiplicity;
    }
}
