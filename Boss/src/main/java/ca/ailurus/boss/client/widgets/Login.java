package ca.ailurus.boss.client.widgets;

import ca.ailurus.boss.client.SyncCallback;
import ca.ailurus.boss.client.events.AppEventBus;
import ca.ailurus.boss.client.events.LoginEvent;
import ca.ailurus.boss.shared.User;
import ca.ailurus.boss.shared.UserService;
import ca.ailurus.boss.shared.UserServiceAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

public class Login extends Composite {
    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
    @UiField
    TextBox usernameTextBox;
    @UiField
    TextBox passwordTextBox;
    @UiField
    Label errorLabel;
    @UiField
    Button submit;
    private UserServiceAsync userService = GWT.create(UserService.class);
    public Login() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiHandler("submit")
    void onLogin(ClickEvent click) {
        String username = usernameTextBox.getText();
        String password = passwordTextBox.getText();
        usernameTextBox.setText("");
        passwordTextBox.setText("");

        userService.login(username, password, SyncCallback.create(new AsyncCallback<User>() {
            @Override
            public void onFailure(Throwable exception) {
                errorLabel.setText(exception.getLocalizedMessage());
                errorLabel.setVisible(true);
            }

            @Override
            public void onSuccess(User user) {
                AppEventBus.EVENT_BUS.fireEvent(new LoginEvent(user));
            }
        }));
    }

    @UiHandler("usernameTextBox")
    void onUserNameChanged(ChangeEvent event) {
        errorLabel.setVisible(false);
    }

    @UiHandler("passwordTextBox")
    void onPsswordChanged(ChangeEvent event) {
        errorLabel.setVisible(false);
    }

    interface MyUiBinder extends UiBinder<Widget, Login> {
    }
}
