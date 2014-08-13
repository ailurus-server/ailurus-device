package ca.ailurus.dashboard.apis;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/device")
public class Device {
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String welcome() {
        return "<html><head></head><body>Welcome!<body></html>";
    }
}