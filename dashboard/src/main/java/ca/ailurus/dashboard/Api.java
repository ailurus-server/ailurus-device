package ca.ailurus.dashboard;

import ca.ailurus.dashboard.api.UserApi;
import ca.ailurus.dashboard.api.AppsApi;
import ca.ailurus.dashboard.api.DeviceApi;

import javax.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;

public class Api extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        HashSet<Class<?>> classes = new HashSet<>();
        classes.add(AppsApi.class);
        classes.add(DeviceApi.class);
        classes.add(UserApi.class);
        return classes;
    }
}
