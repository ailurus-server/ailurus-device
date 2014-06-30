package ca.ailurus.boss.client.widgets;

import ca.ailurus.boss.client.events.ShowDashboardEvent;
import ca.ailurus.boss.client.events.AddAppEvent;
import ca.ailurus.boss.client.events.AppEventBus;
import ca.ailurus.boss.shared.Application;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

public class AppAdder extends Composite {
    FlowPanel panel = new FlowPanel();

    public AppAdder(Application[] applications) {
        panel.clear();
        for (Application app : applications) {
            panel.add(new AppEntry(app, "images/add.png", "Install this application", new AddAppEvent(app)));
        }

        Button cancelButton = new Button("Cancel");
        cancelButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                AppEventBus.EVENT_BUS.fireEvent(new ShowDashboardEvent());
            }
        });

        panel.add(cancelButton);
        initWidget(panel);
    }
}
