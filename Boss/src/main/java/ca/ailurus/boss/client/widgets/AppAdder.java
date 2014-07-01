package ca.ailurus.boss.client.widgets;

import ca.ailurus.boss.client.SyncCallback;
import ca.ailurus.boss.client.events.ShowDashboardEvent;
import ca.ailurus.boss.client.events.AddAppEvent;
import ca.ailurus.boss.client.events.AppEventBus;
import ca.ailurus.boss.shared.Application;
import ca.ailurus.boss.shared.ApplicationService;
import ca.ailurus.boss.shared.ApplicationServiceAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.EventHandler;

public class AppAdder extends Composite {
    private FlowPanel appsPanel = new FlowPanel();

    private ApplicationServiceAsync appService = GWT.create(ApplicationService.class);

    interface MyEventBinder extends EventBinder<AppAdder> {}
    private final MyEventBinder eventBinder = GWT.create(MyEventBinder.class);

    public AppAdder(Application[] applications) {
        eventBinder.bindEventHandlers(this, AppEventBus.EVENT_BUS);

        Button cancelButton = new Button("Cancel");
        cancelButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
            AppEventBus.EVENT_BUS.fireEvent(new ShowDashboardEvent());
            }
        });

        FlowPanel panel = new FlowPanel();
        panel.add(appsPanel);
        panel.add(cancelButton);
        initWidget(panel);

        setApplications(applications);
    }

    private void setApplications(Application[] applications) {
        appsPanel.clear();
        for (Application app : applications) {
            appsPanel.add(new AppEntry(app, "images/add.png", "Install this application", new AddAppEvent(app)));
        }
    }

    @EventHandler
    void onAddApp(AddAppEvent event) {
        String appId = event.getApplication().getId();
        appService.installApp(appId, SyncCallback.create(new AsyncCallback<Application[]>() {
            @Override
            public void onFailure(Throwable throwable) {
                // TODO log the error somewhere
            }
            @Override
            public void onSuccess(Application[] applications) {
                setApplications(applications);
            }
        }));
    }

}
