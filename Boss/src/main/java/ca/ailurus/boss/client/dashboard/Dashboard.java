package ca.ailurus.boss.client.dashboard;

import ca.ailurus.boss.client.events.*;
import ca.ailurus.boss.client.widgets.AppAdder;
import ca.ailurus.boss.client.widgets.AppDetails;
import ca.ailurus.boss.client.widgets.AppViewer;
import ca.ailurus.boss.shared.Application;
import ca.ailurus.boss.shared.Setting;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.EventHandler;

public class Dashboard extends Composite {
    interface MyUiBinder extends UiBinder<Widget, Dashboard> {}
    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    interface MyEventBinder extends EventBinder<Dashboard> {}
    private final MyEventBinder eventBinder = GWT.create(MyEventBinder.class);

    @UiField
    Label titleLabel;
    @UiField
    Image icon;
    @UiField
    FlowPanel panel;
    @UiField
    Button logoutButton;

    public Dashboard() {
        eventBinder.bindEventHandlers(this, AppEventBus.EVENT_BUS);
        initWidget(uiBinder.createAndBindUi(this));

        Application[] applications = new Application[1];
        applications[0] = new Application();

        applications[0].setName("Wordpress");
        applications[0].setDescription("An easy to use framework for setting up web pages.");

        titleLabel.setText("Dashboard");
        icon.setUrl("images/settings.png");
        icon.setAltText("Dashboard Icon");
        panel.add(new AppViewer(applications));
    }

    @UiHandler("logoutButton")
    void onLogout(ClickEvent event) {
        AppEventBus.EVENT_BUS.fireEvent(new LogoutEvent());
    }

    @EventHandler
    void onAddAppRequest(AddAppRequestEvent event) {
        panel.clear();

        Application[] applications = new Application[1];
        applications[0] = new Application();
        applications[0].setName("OwnCloud");
        applications[0].setDescription("A tool for you to manage your own files.");
        panel.add(new AppAdder(applications));
    }

    @EventHandler
    void onShowDashboard(ShowDashboardEvent event) {
        panel.clear();
        Application[] applications = new Application[1];
        applications[0] = new Application();

        applications[0].setName("Wordpress");
        applications[0].setDescription("An easy to use framework for setting up web pages.");
        panel.add(new AppViewer(applications));
    }

    @EventHandler
    void onViewApp(ViewAppEvent event) {
        panel.clear();
        Setting[] settings = new Setting[2];
        settings[0] = new Setting("Something", "SomethingElse");
        settings[1] = new Setting("Something", "SomethingWhat");
        panel.add(new AppDetails(event.getApplication(), settings));
    }
}
