package ca.ailurus.dashboard.api;

import ca.ailurus.entities.Account;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;

@Path("/accounts/{username}")
public class Accounts {

    private class Status {
        public String status;
        public String info;

        private Status(String status, String info) {
            this.status = status;
            this.info = info;
        }
    }

    @Path("/login")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Status login(@PathParam("username") String userName,
                         @FormParam("password") String password) {
        try {
            Account account = Account.get(userName);
            if (null == account || !account.password.equals(password)) {
                return new Status("error", "invalid credentials");
            }
            return new Status("ok", "login succeeded");
        } catch (SQLException e) {
            return new Status("error", "database error");
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Status create(@PathParam("username") String userName,
                         @FormParam("password") String password) {
        try {
            Account.create(userName, password);
            return new Status("ok", "successfully created");
        } catch (SQLException e) {
            return new Status("error", e.toString());
        }
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Status delete(@PathParam("username") String userName) {
        try {
            Account.delete(userName);
            return new Status("ok", "successfully deleted");
        } catch (SQLException e) {
            return new Status("error", e.toString());
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Status update(@PathParam("username") String userName,
                         @FormParam("username") String newUserName,
                         @FormParam("password") String newPassword) {
        try {
            Account account = Account.get(userName);
            if (null != account) {
                account.userName = newUserName;
                account.password = newPassword;
            }
            return new Status("ok", "successfully updated");
        } catch (SQLException e) {
            return new Status("error", e.toString());
        }
    }

}
