package ca.ailurus.dashboard.api;

import ca.ailurus.dashboard.entities.App;
import ca.ailurus.dashboard.entities.UseCase;
import ca.ailurus.dashboard.transaction.Transaction;
import ca.ailurus.dashboard.transaction.TransactionMaker;
import ca.ailurus.dashboard.objects.UseCaseWithApps;
import com.google.inject.Inject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

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

    @GET @Path("/usecase/{usecase}")
    public UseCaseWithApps findByUsecase(@PathParam("usecase") String useCaseName) {
        try (Transaction tx = transactionMaker.make()) {
            UseCase useCase = tx.getUseCase(useCaseName);
            if (useCase == null) {
                throw new NotFoundException("Use case '" + useCaseName + "' not found.");
            }

            UseCaseWithApps useCaseWithApps = new UseCaseWithApps();
            useCaseWithApps.name = useCase.name;
            useCaseWithApps.displayName = useCase.displayName;
            useCaseWithApps.description = useCase.description;
            useCaseWithApps.type = useCase.type;
            useCaseWithApps.apps = tx.appsByUseCase(useCaseName);
            return useCaseWithApps;
        }
    }

    @GET @Path("/search/{keyword}")
    public List<App> findByKeyword(@PathParam("keyword") String keyword) {
        try (Transaction tx = transactionMaker.make()) {
            return tx.searchApps(keyword);
        }
    }

    @GET @Path("/usecases")
    public Map<String, List<UseCase>> getUsecases() {
        try (Transaction tx = transactionMaker.make()) {
            return tx.getAllUseCases();
        }
    }
}
