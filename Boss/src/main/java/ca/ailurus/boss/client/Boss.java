package ca.ailurus.boss.client;

import ca.ailurus.boss.client.dashboard.Dashboard;
import ca.ailurus.boss.client.dashboard.LoginScreen;
import ca.ailurus.boss.client.events.AppEventBus;
import ca.ailurus.boss.client.events.InitializeEvent;
import ca.ailurus.boss.client.events.LoginEvent;
import ca.ailurus.boss.client.events.LogoutEvent;
import ca.ailurus.boss.client.firstboot.FirstBoot;
import ca.ailurus.boss.shared.MachineService;
import ca.ailurus.boss.shared.MachineServiceAsync;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.EventHandler;

public class Boss implements EntryPoint {
    interface MyEventBinder extends EventBinder<Boss> {
    }

    private final MyEventBinder eventBinder = GWT.create(MyEventBinder.class);
    private MachineServiceAsync bossService = GWT.create(MachineService.class);

    @Override
    public void onModuleLoad() {
        eventBinder.bindEventHandlers(this, AppEventBus.EVENT_BUS);

        Resources.INSTANCE.css().ensureInjected();
        RootPanel.get().add(new LoginScreen());
    }

    private void showHostname() {
        AsyncCallback<String> callback = new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert(throwable.getMessage());
            }

            @Override
            public void onSuccess(String hostname) {
                Window.alert(hostname);
            }
        };

        bossService.getHostName(callback);
    }

    @EventHandler
    void onInitialize(InitializeEvent event) {
        RootPanel panel = RootPanel.get();
        panel.clear();
        panel.add(new Dashboard());
    }

    @EventHandler
    void onLogin(LoginEvent event) {
        RootPanel panel = RootPanel.get();
        panel.clear();
        panel.add(new Dashboard());
    }

    @EventHandler
    void onLogout(LogoutEvent event) {
        RootPanel panel = RootPanel.get();
        panel.clear();
        panel.add(new LoginScreen());
    }
}
