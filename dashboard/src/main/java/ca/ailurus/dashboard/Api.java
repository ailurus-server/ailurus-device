package ca.ailurus.dashboard;

import javax.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;

public class Api extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        HashSet<Class<?>> classes = new HashSet<>();
        classes.add(Initializer.class);
        classes.add(Accounts.class);
        classes.add(Device.class);
        return classes;
    }
}