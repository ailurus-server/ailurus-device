package ca.ailurus.dashboard.api;

import ca.ailurus.dashboard.exceptions.InvalidUseCaseException;
import ca.ailurus.dashboard.managers.AppManager;
import ca.ailurus.dashboard.managers.UseCaseManager;
import ca.ailurus.dashboard.objects.UseCaseWithApps;
import ca.ailurus.dashboard.entities.App;
import ca.ailurus.dashboard.entities.UseCase;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/apps")
@Produces(MediaType.APPLICATION_JSON)
public class AppApi {
    private UseCaseManager useCaseManager;
    private AppManager appManager;

    @Inject
    public AppApi(UseCaseManager useCaseManager, AppManager appManager) {
        this.useCaseManager = useCaseManager;
        this.appManager = appManager;
    }

    @GET @Path("/installed")
    public List<App> getInstalled() throws SQLException {
        return appManager.installed();
    }

    @GET @Path("/usecase/{usecase}")
    public UseCaseWithApps findByUsecase(@PathParam("usecase") String useCaseName) throws SQLException {
        UseCase useCase = useCaseManager.find(useCaseName);
        if (useCase == null) {
            throw new InvalidUseCaseException(useCaseName);
        }

        UseCaseWithApps useCaseWithApps = new UseCaseWithApps();
        useCaseWithApps.name = useCase.name;
        useCaseWithApps.displayName = useCase.displayName;
        useCaseWithApps.description = useCase.description;
        useCaseWithApps.type = useCase.type;
        useCaseWithApps.apps = appManager.searchByTag(useCaseName);

        return useCaseWithApps;
    }

    @GET @Path("/search/{keyword}")
    public List<App> findByKeyword(@PathParam("keyword") String keyword) throws SQLException {
        return appManager.search(keyword);
    }

    @GET @Path("/usecases")
    public Map<String, List<UseCase>> getUsecases() {
        Map<String, List<UseCase>> categorized = new HashMap<>();
        for (UseCase useCase : useCaseManager.getAll()) {
            String typeString = useCase.type.toString();

            if(!categorized.containsKey(typeString)) {
                categorized.put(typeString, new ArrayList<UseCase>());
            }

            categorized.get(typeString).add(useCase);
        }
        return categorized;
    }
}
