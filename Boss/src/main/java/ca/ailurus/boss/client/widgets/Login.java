package ca.ailurus.boss.client.widgets;

import ca.ailurus.boss.client.events.AddUserEvent;
import ca.ailurus.boss.client.events.AppEventBus;
import ca.ailurus.boss.client.events.LoginRequestEvent;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;

public class Login extends Composite {
    interface MyUiBinder extends UiBinder<Widget, Login> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    @UiField
    TextBox usernameTextBox;
    @UiField
    TextBox passwordTextBox;
    @UiField
    Label errorLabel;
    @UiField
    Button submit;

    public Login() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiHandler("submit")
    void onLogin(ClickEvent click) {
        String username = usernameTextBox.getText();
        String password = passwordTextBox.getText();

        showSpinner();

        try {
            // TODO handle error messages on login failure
            AppEventBus.EVENT_BUS.fireEvent(new LoginRequestEvent(username, password));
        } catch (Exception exception) {
            errorLabel.setText(exception.getLocalizedMessage());
            errorLabel.setVisible(true);
        }
    }

    void showSpinner() {
        // TODO show the spinner and disable the UI elements
    }

    void hideSpinner() {
        // TODO hide the spinner and enable the UI elements
    }
}