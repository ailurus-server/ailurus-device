package ca.ailurus.dashboard;

import com.google.inject.servlet.ServletModule;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;

import com.google.inject.Singleton;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import java.util.HashMap;
import java.util.Map;

public class DashboardModule extends ServletModule {
    @Override
    protected void configureServlets() {
        bindDashboardEndpoint();
        bindWelcomeEndpoint();
        bindApiEndpoints();
        bindStaticEndpoints();
    }

    private void bindDashboardEndpoint() {
        serve("/").with(Dashboard.class);
    }

    private void bindWelcomeEndpoint() {
        serve("/welcome").with(Welcome.class);
    }

    private void bindApiEndpoints() {
        markAsSingleton(HttpServletDispatcher.class);

        ServletContext context = getServletContext();
        context.setInitParameter("resteasy.servlet.mapping.prefix", "/api");

        serve("/api/*").with(HttpServletDispatcher.class);
    }

    private void bindStaticEndpoints() {
        markAsSingleton(DefaultServlet.class);

        // TODO remove this section when deploying to the device
        // This parameter fixes a Jetty problem in windows
        // where the static files gets locked while the server
        // is running.
        Map<String, String> params = new HashMap<>();
        params.put("useFileMappedBuffer", "false");

        serve("/*").with(DefaultServlet.class, params);
    }

    // Since some servlet classes does not have the Singleton annotation
    // and Guice requires all servlets to have the Singleton scope,
    // we have to set the scope of this class manually.
    private <T extends HttpServlet> void markAsSingleton(Class<T> servletClass) {
        bind(servletClass).in(Singleton.class);
    }
}