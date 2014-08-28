package ca.ailurus.dashboard;

import ca.ailurus.dashboard.entities.DeviceSettings;
import org.apache.http.client.RedirectException;
import org.jboss.resteasy.plugins.providers.html.Redirect;
import org.jboss.resteasy.plugins.providers.html.Renderable;
import org.jboss.resteasy.plugins.providers.html.View;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;

@Path("/welcome")
@Produces(MediaType.TEXT_HTML)
public class Welcome {
    @GET
    public Renderable display() throws SQLException {

        DeviceSettings settings = DeviceSettings.getSettings();

        boolean initialized = settings.initialized;
        if (initialized) {
            return new Redirect("");
        }

        return new View("jsp/welcome.jsp");
    }
}