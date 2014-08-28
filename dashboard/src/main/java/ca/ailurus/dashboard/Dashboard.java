package ca.ailurus.dashboard;

import org.jboss.resteasy.plugins.providers.html.View;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("")
public class Dashboard {
    @GET
    @Produces(MediaType.TEXT_HTML)
    public View display() {
        return new View("jsp/dashboard.jsp");
    }
}