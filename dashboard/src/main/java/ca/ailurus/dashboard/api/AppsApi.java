package ca.ailurus.dashboard.api;

import ca.ailurus.entities.App;
import ca.ailurus.entities.UseCase;

import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/apps")
@Produces(MediaType.APPLICATION_JSON)
public class AppsApi {
    @GET
    @Path("/installed")
    public List<App> getInstalled() {
        try {
            return App.installed();
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }

    @GET @Path("/usecases")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, List<UseCase>> getUsecases() {
        Map<String, List<UseCase>> categorized = new HashMap<>();
        for (UseCase useCase : UseCase.getAll()) {
            String typeString = useCase.type.toString();

            if(!categorized.containsKey(typeString)) {
                categorized.put(typeString, new ArrayList<UseCase>());
            }

            categorized.get(typeString).add(useCase);
        }
        return categorized;
    }
}
