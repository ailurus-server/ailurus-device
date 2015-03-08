package ca.ailurus.dashboard.api;

import ca.ailurus.dashboard.service.DomainService;
import com.google.inject.Inject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/network")
@Produces(MediaType.APPLICATION_JSON)
public class NetworkApi {

    private DomainService domainService;

    @Inject
    public NetworkApi(DomainService domainService) {
        this.domainService = domainService;
    }

    @GET @Path("/dns/{subDomain}")
    public List<String> getRecord(@PathParam("subDomain") String subDomain) {
        return domainService.getRecords(subDomain);
    }

    @PUT @Path("/dns/{subDomain}")
    public List<String> addRecord(@PathParam("subDomain") String subDomain) {
        return domainService.getRecords(subDomain);
    }
}
