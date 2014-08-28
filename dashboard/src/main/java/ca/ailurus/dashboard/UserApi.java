package ca.ailurus.dashboard;

import ca.ailurus.dashboard.exceptions.UserAuthenticationError;
import ca.ailurus.dashboard.entities.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;

@Path("/accounts/{name}")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserApi {

    private class Status {
        public String status;
        public String info;
        public String token;

        private Status(String status, String info, String token) {
            this.status = status;
            this.info = info;
            this.token = token;
        }

        private Status(String status, String info) {
            this(status, info, null);
        }
    }

    @POST  @Path("/login")
    public Status login(User user) {
        try {
            User account = User.get(user.name);
            if (null == account || !account.password.equals(user.password)) {
                throw new UserAuthenticationError();
            }
            return new Status("ok", "login succeeded", user.name + ":" + user.password);
        } catch (SQLException e) {
            throw new InternalServerErrorException(e);
        }
    }

    @PUT
    public Status create(User user) {
        try {
            User.create(user);
            return new Status("ok", "successfully created");
        } catch (SQLException e) {
            throw new InternalServerErrorException(e);
        }
    }

    @DELETE
    public Status delete(@PathParam("name") String userName) {
        try {
            User.delete(userName);
            return new Status("ok", "successfully deleted");
        } catch (SQLException e) {
            throw new InternalServerErrorException(e);
        }
    }

    @POST
    public Status update(@PathParam("name") String userName,
                         User user) {
        try {
            User account = User.get(userName);
            if (null != account) {
                account.name = user.name;
                account.password = user.password;
                return new Status("ok", "successfully updated");
            }
            throw new BadRequestException("Invalid account.");
        } catch (SQLException e) {
            throw new InternalServerErrorException(e);
        }
    }

}
