package ca.ailurus.dashboard.api;

import ca.ailurus.dashboard.api.exceptions.AlreadyInitializedException;
import ca.ailurus.dashboard.api.objects.Initialization;
import ca.ailurus.entities.DeviceSettings;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;

@Path("/init")
public class Initializer {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void init(Initialization settings) throws SQLException {
        DeviceSettings device = DeviceSettings.getSettings();

        if (device.initialized) {
            throw new AlreadyInitializedException();
        }

        device.initialized = true;
        DeviceSettings.update(device);
    }
}