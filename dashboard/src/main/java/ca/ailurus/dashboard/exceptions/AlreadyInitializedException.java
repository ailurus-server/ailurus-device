package ca.ailurus.dashboard.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class AlreadyInitializedException extends WebApplicationException {
    public AlreadyInitializedException() {
        super(Response.status(Response.Status.BAD_REQUEST)
                .entity("Device has already been initialized.")
                .type(MediaType.TEXT_PLAIN)
                .build());
    }
}
