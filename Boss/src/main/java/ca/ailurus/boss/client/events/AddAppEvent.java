package ca.ailurus.boss.client.events;

import ca.ailurus.boss.shared.Application;
import com.google.web.bindery.event.shared.binder.GenericEvent;

public class AddAppEvent extends GenericEvent {
    private Application application;

    public AddAppEvent(Application app) {
        application = app;
    }

    public Application getApplication() {
        return application;
    }
}
