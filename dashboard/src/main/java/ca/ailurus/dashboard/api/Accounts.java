package ca.ailurus.dashboard.api;

import ca.ailurus.dashboard.api.exceptions.UserAuthenticationError;
import ca.ailurus.dashboard.api.objects.User;
import ca.ailurus.entities.Account;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;

@Path("/accounts/{username}")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Accounts {

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

    @Path("/login")
    @POST
    public Status login(User user) {
        try {
            Account account = Account.get(user.username);
            if (null == account || !account.password.equals(user.password)) {
                throw new UserAuthenticationError();
            }
            return new Status("ok", "login succeeded", user.username + ":" + user.password);
        } catch (SQLException e) {
            throw new InternalServerErrorException(e);
        }
    }

    @PUT
    public Status create(User user) {
        try {
            Account.create(user.username, user.password);
            return new Status("ok", "successfully created");
        } catch (SQLException e) {
            throw new InternalServerErrorException(e);
        }
    }

    @DELETE
    public Status delete(@PathParam("username") String userName) {
        try {
            Account.delete(userName);
            return new Status("ok", "successfully deleted");
        } catch (SQLException e) {
            throw new InternalServerErrorException(e);
        }
    }

    @POST
    public Status update(@PathParam("username") String userName,
                         User user) {
        try {
            Account account = Account.get(userName);
            if (null != account) {
                account.userName = user.username;
                account.password = user.password;
                return new Status("ok", "successfully updated");
            }
            throw new BadRequestException("Invalid account.");
        } catch (SQLException e) {
            throw new InternalServerErrorException(e);
        }
    }

}
