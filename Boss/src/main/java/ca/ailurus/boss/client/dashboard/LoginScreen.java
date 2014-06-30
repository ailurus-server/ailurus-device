package ca.ailurus.boss.client.dashboard;

import ca.ailurus.boss.client.events.AppEventBus;
import ca.ailurus.boss.client.events.LoginEvent;
import ca.ailurus.boss.client.events.LoginRequestEvent;
import ca.ailurus.boss.client.widgets.CenteredFrame;
import ca.ailurus.boss.client.widgets.Login;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.EventHandler;

public class LoginScreen extends Composite {
    interface MyEventBinder extends EventBinder<LoginScreen> {}
    private final MyEventBinder eventBinder = GWT.create(MyEventBinder.class);

    public LoginScreen() {
        eventBinder.bindEventHandlers(this, AppEventBus.EVENT_BUS);

        CenteredFrame frame = new CenteredFrame("Login", "images/login.png", "Login Icon");
        frame.add(new Login());
        initWidget(frame);
    }

    @EventHandler
    void onLoginRequest(LoginRequestEvent event) {
        // TODO remove this method
        AppEventBus.EVENT_BUS.fireEvent(new LoginEvent());
    }
}
