package ca.ailurus.boss.client.widgets;

import ca.ailurus.boss.client.events.ViewAppEvent;
import ca.ailurus.boss.shared.Application;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

public class AppViewer extends Composite {
    FlowPanel panel = new FlowPanel();

    public AppViewer(Application[] applications) {
        panel.clear();
        for (Application app : applications) {
            AppEntry appEntry = new AppEntry(app, "images/settings.png", "Configure this application", new ViewAppEvent(app));
            panel.add(appEntry);
        }
        panel.add(new AppAdderEntry());
        initWidget(panel);
    }
}
