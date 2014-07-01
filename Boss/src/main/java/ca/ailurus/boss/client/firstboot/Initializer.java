package ca.ailurus.boss.client.firstboot;


import ca.ailurus.boss.client.events.AddUserEvent;
import ca.ailurus.boss.client.events.AppEventBus;
import ca.ailurus.boss.client.events.InitializeEvent;
import ca.ailurus.boss.client.widgets.CenteredFrame;
import ca.ailurus.boss.client.widgets.UserAdder;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.EventHandler;

public class Initializer extends Composite {
    interface MyEventBinder extends EventBinder<Initializer> {}
    private final MyEventBinder eventBinder = GWT.create(MyEventBinder.class);

    public Initializer() {
        eventBinder.bindEventHandlers(this, AppEventBus.EVENT_BUS);

        CenteredFrame frame = new CenteredFrame("Add Your First User", "images/userManagement.png", "Add User Icon");
        frame.add(new UserAdder());
        initWidget(frame);
    }

    @EventHandler
    void onAddUser(AddUserEvent event) {
        Window.alert(event.getUsername() + " " + event.getPassword());
        AppEventBus.EVENT_BUS.fireEvent(new InitializeEvent());
    }
}