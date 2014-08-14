package ca.ailurus.dashboard.api;

import ca.ailurus.entities.DeviceSettings;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;

@Path("/device")
public class Device {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public DeviceSettings init() throws SQLException {
        return DeviceSettings.getSettings();
    }
}