package ca.ailurus.boss.client;

import ca.ailurus.boss.client.dashboard.Dashboard;
import ca.ailurus.boss.client.dashboard.LoginScreen;
import ca.ailurus.boss.client.events.*;
import ca.ailurus.boss.client.firstboot.FirstBoot;
import ca.ailurus.boss.shared.MachineService;
import ca.ailurus.boss.shared.MachineServiceAsync;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.EventHandler;

public class Boss implements EntryPoint {
    interface MyEventBinder extends EventBinder<Boss> {
    }

    private final MyEventBinder eventBinder = GWT.create(MyEventBinder.class);
    @Override
    public void onModuleLoad() {
        eventBinder.bindEventHandlers(this, AppEventBus.EVENT_BUS);

        Resources.INSTANCE.css().ensureInjected();
        RootPanel.get().add(new FirstBoot());
    }

    @EventHandler
    void onInitialize(InitializeEvent event) {
        RootPanel panel = RootPanel.get();
        panel.clear();
        panel.add(new Dashboard(event.getUser()));
    }

    @EventHandler
    void onLogin(LoginEvent event) {
        RootPanel panel = RootPanel.get();
        panel.clear();
        panel.add(new Dashboard(event.getUser()));
    }

    @EventHandler
    void onLogout(LogoutEvent event) {
        RootPanel panel = RootPanel.get();
        panel.clear();
        panel.add(new LoginScreen());
    }
}
