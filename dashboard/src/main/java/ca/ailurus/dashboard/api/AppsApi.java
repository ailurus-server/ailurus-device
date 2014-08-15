package ca.ailurus.dashboard.api;

import ca.ailurus.entities.App;

import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.List;

@Path("/apps")
@Produces(MediaType.APPLICATION_JSON)
public class AppsApi {
    @GET
    @Path("/installed")
    public List<App> appsInstalled() {
        try {
            return App.installed();
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }
}
