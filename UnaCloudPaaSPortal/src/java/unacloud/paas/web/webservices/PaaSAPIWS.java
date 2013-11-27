/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unacloud.paas.web.webservices;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import unacloud.paas.back.cluster.platforms.ClusterManager;
import unacloud.paas.data.entities.MainCommand;
import unacloud.paas.data.entities.PuppetModuleUsage;
import unacloud.paas.data.entities.enums.ResourceType;
import unacloud.paas.data.execution.CommandExecutionEntity;
import unacloud.paas.data.execution.FileDescriptionEntity;
import unacloud.paas.data.execution.PlatformExecutionEntity;
import unacloud.paas.data.execution.RoleExecutionEntity;
import unacloud.paas.data.managers.PlatformExecutionManager;
import unacloud.paas.data.managers.PuppetModuleManager;

/**
 * REST Web Service
 *
 * @author Clouder
 */
@Path("paasapi")
public class PaaSAPIWS {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of PaaSAPIWS
     */
    public PaaSAPIWS() {
    }

    /**
     * Retrieves representation of an instance of unacloud.paas.web.webservices.PaaSAPIWS
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of PaaSAPIWS
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(int platformId,int cores){
        
    }
}
