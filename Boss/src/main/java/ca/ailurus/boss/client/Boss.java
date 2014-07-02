package ca.ailurus.boss.client;

import ca.ailurus.boss.client.dashboard.Dashboard;
import ca.ailurus.boss.client.dashboard.LoginScreen;
import ca.ailurus.boss.client.events.AppEventBus;
import ca.ailurus.boss.client.events.InitializeEvent;
import ca.ailurus.boss.client.events.LoginEvent;
import ca.ailurus.boss.client.events.LogoutEvent;
import ca.ailurus.boss.client.firstboot.FirstBoot;
import ca.ailurus.boss.shared.User;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.EventHandler;

public class Boss implements EntryPoint {
    private final MyEventBinder eventBinder = GWT.create(MyEventBinder.class);

    @Override
    public void onModuleLoad() {
        eventBinder.bindEventHandlers(this, AppEventBus.EVENT_BUS);

        Resources.INSTANCE.css().ensureInjected();

        boolean isInitialized = getJavaScriptBool("isInitialized");

        if (!isInitialized) {
            RootPanel.get().add(new FirstBoot());
        } else {
            RootPanel.get().add(new LoginScreen());
        }
    }

    private boolean getJavaScriptBool(String name) {
        Element window = (Element) Document.get().<Element>cast().getPropertyObject("defaultView");
        return window.getPropertyBoolean(name);
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

    interface MyEventBinder extends EventBinder<Boss> {
    }
}
