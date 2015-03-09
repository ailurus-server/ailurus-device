package ca.ailurus.dashboard.inject;

import ca.ailurus.dashboard.ApplicationServletModule;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class GuiceServletConfig extends GuiceServletContextListener {
    @Override
    protected Injector getInjector() {
        return DashboardInjector.injector.createChildInjector(new ApplicationServletModule());
    }
}
