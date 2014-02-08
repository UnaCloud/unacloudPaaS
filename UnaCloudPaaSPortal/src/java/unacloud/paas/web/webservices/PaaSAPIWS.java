/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unacloud.paas.web.webservices;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;

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
