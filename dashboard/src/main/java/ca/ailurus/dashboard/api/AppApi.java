package ca.ailurus.dashboard.api;

import ca.ailurus.dashboard.entities.App;
import ca.ailurus.dashboard.transaction.Transaction;
import ca.ailurus.dashboard.transaction.TransactionMaker;
import com.google.inject.Inject;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/apps")
@Produces(MediaType.APPLICATION_JSON)
public class AppApi {
    private static String AILURUSD_URL = "http://localhost:18570/";

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
            App app = tx.getApp(name);
            if (app.installed || app.progress != -1) {
                throw new BadRequestException("App is installed or installing.");
            }

            HttpRequest request = Unirest.post(AILURUSD_URL + "install/" + app.name);

            try {
                request.asString();
            } catch(UnirestException e) {
                throw new InternalServerErrorException(e);
            }

            tx.startInstallApp(name);
            tx.commit();
        }
    }

    @POST @Path("/progress/{name}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateProgress(@PathParam("name") String name, int progress) {
        try (Transaction tx = transactionMaker.make()) {
            tx.updateAppProgress(name, progress);
            tx.commit();
        }
    }


    @DELETE @Path("/named/{name}")
    public void startUninstall(@PathParam("name") String name) {
        try (Transaction tx = transactionMaker.make()) {
            App app = tx.getApp(name);
            if (!app.installed || app.progress != -1) {
                throw new BadRequestException("App is uninstalled or uninstalling.");
            }

            HttpRequest request = Unirest.post(AILURUSD_URL + "uninstall/" + name);
            try {
                request.asString();
            } catch(UnirestException e) {
                throw new InternalServerErrorException(e);
            }

            tx.startUninstallApp(name);
            tx.commit();
        }
    }
}
