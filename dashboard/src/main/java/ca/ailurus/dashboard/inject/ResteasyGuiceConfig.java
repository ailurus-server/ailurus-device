package ca.ailurus.dashboard.inject;

import ca.ailurus.dashboard.api.ApiModule;
import com.google.inject.Injector;
import com.google.inject.Module;
import org.jboss.resteasy.plugins.guice.GuiceResteasyBootstrapServletContextListener;

import javax.servlet.ServletContext;
import java.util.Arrays;
import java.util.List;

public class ResteasyGuiceConfig extends GuiceResteasyBootstrapServletContextListener {

    @Override
    protected void withInjector(Injector injector) {
        // Stores a reference the injector so that the GuiceServletConfig can
        // use it to create a child injector. This way, singletons can be
        // shared between the two injectors.
        DashboardInjector.injector = injector;
    }

    @Override
    protected List<? extends Module> getModules(ServletContext context) {
        return Arrays.asList(new ApiModule());
    }
}
