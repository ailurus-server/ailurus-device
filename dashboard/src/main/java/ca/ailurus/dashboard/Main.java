package ca.ailurus.dashboard;

import ca.ailurus.dashboard.inject.GuiceServletConfig;
import ca.ailurus.dashboard.inject.ResteasyGuiceConfig;
import com.google.inject.servlet.GuiceFilter;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.*;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;


public class Main {
    public static void main(String args[]) throws Exception {
        //Injector injector = Guice.createInjector(new ApplicationServletModule());

        Server server = new Server(8080);

        ResourceHandler resourceHandler = new ResourceHandler() {
            @Override
            public Resource getResource(String path) {
                return Resource.newClassPathResource("META-INF/resources" + path);
            }
        };

        WebAppContext webAppContext = new WebAppContext();
        webAppContext.setContextPath("/");
        webAppContext.setResourceBase("src/main/webapp/");
        webAppContext.setParentLoaderPriority(true);
        webAppContext.addEventListener(new ResteasyGuiceConfig());
        webAppContext.setInitParameter("resteasy.servlet.mapping.prefix", "/api");
        webAppContext.addEventListener(new GuiceServletConfig());
        webAppContext.addFilter(GuiceFilter.class, "/*", null);

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resourceHandler, webAppContext});

        server.setHandler(handlers);

        server.start();
        server.join();
    }
}
