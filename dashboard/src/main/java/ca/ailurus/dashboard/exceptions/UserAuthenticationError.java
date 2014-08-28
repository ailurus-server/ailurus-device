package ca.ailurus.dashboard.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class UserAuthenticationError extends WebApplicationException {
    public UserAuthenticationError() {
        super(Response.status(Response.Status.FORBIDDEN)
                .entity("Authentication failed.")
                .type(MediaType.TEXT_PLAIN)
                .build());
    }
}
