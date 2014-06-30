package ca.ailurus.boss.client.events;

import ca.ailurus.boss.shared.Application;
import com.google.web.bindery.event.shared.binder.GenericEvent;

/**
 * Created by Me on 2014-06-30.
 */
public class ViewAppEvent extends GenericEvent {
    public ViewAppEvent(Application app) {
        application = app;
    }

    public Application getApplication() {
        return application;
    }

    Application application;
}
