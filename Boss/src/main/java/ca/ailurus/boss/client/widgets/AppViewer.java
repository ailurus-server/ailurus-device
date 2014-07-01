package ca.ailurus.boss.client.widgets;

import ca.ailurus.boss.client.SyncCallback;
import ca.ailurus.boss.client.events.ViewAppEvent;
import ca.ailurus.boss.shared.Application;
import ca.ailurus.boss.shared.ApplicationService;
import ca.ailurus.boss.shared.ApplicationServiceAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

public class AppViewer extends Composite {
    private FlowPanel panel = new FlowPanel();

    private ApplicationServiceAsync appService = GWT.create(ApplicationService.class);

    public AppViewer(Application[] applications) {
        panel.add(new AppAdderEntry());
        initWidget(panel);

        setApplications(applications);
    }

    private void setApplications(Application[] applications) {
        panel.clear();
        for (Application app : applications) {
            AppEntry appEntry = new AppEntry(app, "images/settings.png", "Configure this application", new ViewAppEvent(app));
            panel.add(appEntry);
        }
        panel.add(new AppAdderEntry());
    }
}
