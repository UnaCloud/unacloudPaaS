/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unacloud.paas.back.iaasservices;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author G
 */
public class DeployedCluster {
    long id;
    List<DeployedClusterRol> roles=new ArrayList<>();
    public DeployedCluster(long id) {
        this.id = id;
    }
    public long getId() {
        return id;
    }
    public List<DeployedClusterRol> getRoles() {
        return roles;
    }
}
