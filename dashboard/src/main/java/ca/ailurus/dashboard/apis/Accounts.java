package ca.ailurus.dashboard.apis;

import ca.ailurus.entities.Account;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;

@Path("/accounts")
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
    public Status login(@FormParam("username") String userName,
                         @FormParam("password") String password) {
        try {
            Account account = Account.getAccount(userName);
            if (null == account || !account.getPassword().equals(password)) {
                return new Status("error", "invalid credentials");
            }
            return new Status("ok", "login succeeded");
        } catch (SQLException e) {
            return new Status("error", "database error");
        }
    }

    @Path("/create")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Status create(@FormParam("username") String userName,
                         @FormParam("password") String password) {
        try {
            Account.createAccount(userName, password);
            return new Status("ok", "successfully created");
        } catch (SQLException e) {
            return new Status("error", e.toString());
        }
    }
}
