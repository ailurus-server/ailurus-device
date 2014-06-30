package ca.ailurus.boss.client.events;

import ca.ailurus.boss.shared.Application;
import com.google.web.bindery.event.shared.binder.GenericEvent;

/**
 * Created by Me on 2014-06-30.
 */
public class AppSelectionEvent extends GenericEvent {
    public AppSelectionEvent(Application application) {
        this.application = application;
    }

    public Application getApplication() {
        return application;
    }

    private final Application application;
}
