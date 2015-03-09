package ca.ailurus.dashboard;

import ca.ailurus.dashboard.api.ApiModule;
import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;

public class ApplicationServletModule extends ServletModule {
    @Override
    protected void configureServlets() {
        //install(new ApiModule());

        bind(DashboardServlet.class);
        serve("/dashboard").with(DashboardServlet.class);

        bind(WelcomeServlet.class);
        serve("/welcome").with(WelcomeServlet.class);

        bind(HttpServletDispatcher.class).in(Singleton.class);
        serve("/api/*").with(HttpServletDispatcher.class);
    }
}
