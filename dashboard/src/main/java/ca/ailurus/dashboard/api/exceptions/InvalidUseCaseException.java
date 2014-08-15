package ca.ailurus.dashboard.api.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class InvalidUseCaseException extends WebApplicationException {
    public InvalidUseCaseException(String usecase) {
        super(Response.status(Response.Status.BAD_REQUEST)
                .entity("\"" + usecase + "\" is not a valid application use case name.")
                .type(MediaType.TEXT_PLAIN)
                .build());
    }
}
