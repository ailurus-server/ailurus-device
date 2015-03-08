package ca.ailurus.dashboard.api;

import ca.ailurus.dashboard.entities.App;
import ca.ailurus.dashboard.transaction.Transaction;
import ca.ailurus.dashboard.transaction.TransactionMaker;
import com.google.inject.Inject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/apps")
@Produces(MediaType.APPLICATION_JSON)
public class AppApi {
    TransactionMaker transactionMaker;

    @Inject
    public AppApi(TransactionMaker transactionMaker) {
        this.transactionMaker = transactionMaker;
    }

    @GET @Path("/installed")
    public List<App> getInstalled() {
        try (Transaction tx = transactionMaker.make()) {
            return tx.getInstalledApps();
        }
    }

    @GET @Path("/featured")
    public List<App> getFeatured() {
        try (Transaction tx = transactionMaker.make()) {
            return tx.getFeaturedApps();
        }
    }

    @GET @Path("/search/{keyword}")
    public List<App> findByKeyword(@PathParam("keyword") String keyword) {
        try (Transaction tx = transactionMaker.make()) {
            return tx.searchApps(keyword);
        }
    }

    @PUT @Path("/named/{name}")
    public void startInstall(@PathParam("name") String name) {
        try (Transaction tx = transactionMaker.make()) {
            tx.startInstallApp(name);
            tx.commit();
        }
    }

    @DELETE @Path("/named/{name}")
    public void startUninstall(@PathParam("name") String name) {
        try (Transaction tx = transactionMaker.make()) {
            tx.startUninstallApp(name);
            tx.commit();
        }
    }
}
