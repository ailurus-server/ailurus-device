package ca.ailurus.boss.client.firstboot;


import ca.ailurus.boss.client.SyncCallback;
import ca.ailurus.boss.client.events.AddUserEvent;
import ca.ailurus.boss.client.events.AppEventBus;
import ca.ailurus.boss.client.events.InitializeEvent;
import ca.ailurus.boss.client.widgets.CenteredFrame;
import ca.ailurus.boss.client.widgets.UserAdder;
import ca.ailurus.boss.shared.MachineService;
import ca.ailurus.boss.shared.MachineServiceAsync;
import ca.ailurus.boss.shared.User;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.EventHandler;

public class Initializer extends Composite {
    private final MyEventBinder eventBinder = GWT.create(MyEventBinder.class);
    private MachineServiceAsync machineService = GWT.create(MachineService.class);

    public Initializer() {
        eventBinder.bindEventHandlers(this, AppEventBus.EVENT_BUS);

        HTMLPanel panel = new HTMLPanel("");
        panel.add(new UserAdder());
        initWidget(panel);

        /*CenteredFrame frame = new CenteredFrame("Add Your First User", "images/userManagement.png", "Add User Icon");
        frame.add(new UserAdder());
        initWidget(frame);*/
    }

    @EventHandler
    void onAddUser(AddUserEvent event) {
        final User user = event.getUser();
        machineService.initialize(user, SyncCallback.create(new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable throwable) {
                // TODO log this error somewhere
                Window.alert(throwable.getLocalizedMessage());
            }

            @Override
            public void onSuccess(Void dummy) {
                AppEventBus.EVENT_BUS.fireEvent(new InitializeEvent(user));
            }
        }));
    }

    interface MyEventBinder extends EventBinder<Initializer> {
    }
}
