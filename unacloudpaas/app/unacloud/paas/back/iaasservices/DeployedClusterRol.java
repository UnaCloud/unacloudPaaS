/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unacloud.paas.back.iaasservices;

import unacloudws.responses.VirtualMachineExecutionWS;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author G
 */
public class DeployedClusterRol {
    long id;
    List<VirtualMachineExecutionWS> nodes;
    public DeployedClusterRol(long id){
        this.id = id;
        this.nodes = new ArrayList<>();
    }
    public long getId() {
        return id;
    }
    public List<VirtualMachineExecutionWS> getNodes() {
        return nodes;
    }
    public void setNodes(List<VirtualMachineExecutionWS> nodes) {
        this.nodes = nodes;
    }
}
