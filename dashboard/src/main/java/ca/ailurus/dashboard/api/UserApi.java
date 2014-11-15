package ca.ailurus.dashboard.api;

import ca.ailurus.dashboard.entities.User;
import ca.ailurus.dashboard.transaction.Transaction;
import ca.ailurus.dashboard.transaction.TransactionMaker;
import com.google.inject.Inject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/accounts/{name}")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserApi {
    TransactionMaker transactonManager;

    @Inject
    public UserApi(TransactionMaker transactonManager) {
        this.transactonManager = transactonManager;
    }

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
        try (Transaction tx = transactonManager.make()) {
            User account = tx.getUser(user.name);
            if (null == account || !account.password.equals(user.password)) {
                throw new NotAuthorizedException("Authorization Failed");
            }
            return new Status("ok", "login succeeded", user.name + ":" + user.password);
        }
    }

    @PUT
    public Status create(User user) {
        try (Transaction tx = transactonManager.make()) {
            tx.addUser(user);
            tx.commit();
            return new Status("ok", "successfully created");
        }
    }

    @DELETE
    public Status delete(@PathParam("name") String userName) {
        try (Transaction tx = transactonManager.make()) {
            tx.deleteUser(userName);
            tx.commit();
            return new Status("ok", "successfully deleted");
        }
    }

    @POST
    public Status update(@PathParam("name") String userName, User user) {
        try (Transaction tx = transactonManager.make()) {
            User account = tx.getUser(userName);
            if (account == null) {
                throw new BadRequestException("Invalid account.");
            }

            account.name = user.name;
            account.password = user.password;
            tx.setUser(account);
            tx.commit();
            return new Status("ok", "successfully updated");
        }
    }

}
