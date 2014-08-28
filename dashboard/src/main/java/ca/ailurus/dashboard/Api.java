package ca.ailurus.dashboard;

import ca.ailurus.dashboard.api.AppApi;
import ca.ailurus.dashboard.api.DeviceApi;
import ca.ailurus.dashboard.api.UserApi;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@Path("/")
public class Api extends Application {
    private Set<Object> singletons = new HashSet<>();

    @Inject
    public Api() {
        singletons.add(new AppApi());
        singletons.add(new DeviceApi());
        singletons.add(new UserApi());
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
