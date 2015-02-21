package ca.ailurus.dashboard.api;

import ca.ailurus.dashboard.entities.User;
import ca.ailurus.dashboard.transaction.Transaction;
import ca.ailurus.dashboard.transaction.TransactionMaker;
import com.google.inject.Inject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsersApi {
    TransactionMaker transactionMaker;

    @Inject
    public UsersApi(TransactionMaker transactionMaker) {
        this.transactionMaker = transactionMaker;
    }

    @POST  @Path("/{name}/login")
    public User login(@PathParam("name") String name, String password) {
        try (Transaction tx = transactionMaker.make()) {
            User user = tx.getUser(name);
            // TODO: compare users password against bcrypted hash
            if (null == user || !user.password.equals(password)) {
                throw new NotAuthorizedException("Authorization Failed");
            }
            return user;
        }
    }

    @GET
    public ArrayList<User> list() {
        try (Transaction tx = transactionMaker.make()) {
            return tx.listUsers();
        }
    }

    @PUT
    public String create(User user) {
        try (Transaction tx = transactionMaker.make()) {
            tx.addUser(user);
            tx.commit();
            return "created";
        }
    }

    @DELETE @Path("/{name}")
    public String delete(@PathParam("name") String userName) {
        try (Transaction tx = transactionMaker.make()) {
            tx.deleteUser(userName);
            tx.commit();
            return "deleted";
        }
    }

    @POST @Path("/{name}")
    public String update(@PathParam("name") String userName, User update) {
        try (Transaction tx = transactionMaker.make()) {
            User user = tx.getUser(userName);
            if (user == null) {
                throw new BadRequestException("Invalid user.");
            }

            if (update.name != null) {
                User oldUser = tx.getUser(update.name);
                if (oldUser != null) {
                    throw new BadRequestException(userName + " is already taken.");
                }

                tx.deleteUser(user.name);
                user.name = update.name;
            }

            if (update.email != null) {
                user.email = update.email;
            }

            if (update.password != null) {
                user.password = update.password;
            }

            tx.commit();
            return "updated";
        }
    }
}
