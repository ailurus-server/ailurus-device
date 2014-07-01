package ca.ailurus.boss.client.dashboard;

import ca.ailurus.boss.client.SyncCallback;
import ca.ailurus.boss.client.events.*;
import ca.ailurus.boss.client.widgets.AppAdder;
import ca.ailurus.boss.client.widgets.AppDetails;
import ca.ailurus.boss.client.widgets.AppViewer;
import ca.ailurus.boss.shared.*;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.EventHandler;

public class Dashboard extends Composite {
    interface MyUiBinder extends UiBinder<Widget, Dashboard> {}
    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    interface MyEventBinder extends EventBinder<Dashboard> {}
    private final MyEventBinder eventBinder = GWT.create(MyEventBinder.class);

    private UserServiceAsync userService = GWT.create(UserService.class);
    private ApplicationServiceAsync appService = GWT.create(ApplicationService.class);

    @UiField
    Label titleLabel;
    @UiField
    Image icon;
    @UiField
    FlowPanel panel;
    @UiField
    Button logoutButton;

    User user;

    public Dashboard(User user) {
        this.user = user;

        eventBinder.bindEventHandlers(this, AppEventBus.EVENT_BUS);
        initWidget(uiBinder.createAndBindUi(this));

        titleLabel.setText("Dashboard");
        icon.setUrl("images/settings.png");
        icon.setAltText("Dashboard Icon");

        appService.getInstalledApps(SyncCallback.create(new AsyncCallback<Application[]>() {
            @Override
            public void onFailure(Throwable throwable) {
                // TODO log the error somewhere
            }

            @Override
            public void onSuccess(Application[] applications) {
                panel.clear();
                panel.add(new AppViewer(applications));
            }
        }));
    }

    @UiHandler("logoutButton")
    void onLogout(ClickEvent event) {
        userService.logout(user, SyncCallback.create(new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable exception) {
                // TODO log the error somewhere
            }

            @Override
            public void onSuccess(Void placeholder) {
                AppEventBus.EVENT_BUS.fireEvent(new LogoutEvent());
            }
        }));
    }

    @EventHandler
    void onAddAppRequest(AddAppRequestEvent event) {
        appService.getAvailableApps(SyncCallback.create(new AsyncCallback<Application[]>() {
            @Override
            public void onFailure(Throwable throwable) {
                // TODO log the error somewhere
            }

            @Override
            public void onSuccess(Application[] applications) {
                panel.clear();
                panel.add(new AppAdder(applications));
            }
        }));
    }

    @EventHandler
    void onShowDashboard(ShowDashboardEvent event) {
        appService.getInstalledApps(SyncCallback.create(new AsyncCallback<Application[]>() {
            @Override
            public void onFailure(Throwable throwable) {
                // TODO log the error somewhere
            }

            @Override
            public void onSuccess(Application[] applications) {
                panel.clear();
                panel.add(new AppViewer(applications));
            }
        }));
    }

    @EventHandler
    void onViewApp(final ViewAppEvent event) {
        final Application app = event.getApplication();
        String appId = app.getId();
        appService.getSettings(appId, SyncCallback.create(new AsyncCallback<Setting[]>() {
            @Override
            public void onFailure(Throwable throwable) {
                // TODO log the error somewhere
            }
            @Override
            public void onSuccess(Setting[] settings) {
                panel.clear();
                panel.add(new AppDetails(app, settings));
            }
        }));
    }
}
