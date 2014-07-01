package ca.ailurus.boss.client.widgets;

import ca.ailurus.boss.client.SyncCallback;
import ca.ailurus.boss.client.events.AddUserEvent;
import ca.ailurus.boss.client.events.AppEventBus;
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

public class UserAdder extends Composite {
    interface MyUiBinder extends UiBinder<Widget, UserAdder> {}
    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    private UserServiceAsync userService = GWT.create(UserService.class);

    @UiField
    TextBox usernameTextBox;
    @UiField
    TextBox passwordTextBox;
    @UiField
    TextBox confirmTextBox;
    @UiField
    Label errorLabel;
    @UiField
    Button nextButton;

    public UserAdder() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiHandler("usernameTextBox")
    void usernameChanged(ChangeEvent event) {
        validateInputs();
    }

    @UiHandler("passwordTextBox")
    void passwordChanged(ChangeEvent event) {
        validateInputs();
    }

    @UiHandler("confirmTextBox")
    void confirmChanged(ChangeEvent event) {
        validateInputs();
    }

    private void validateInputs() {
        boolean valid = true;
        errorLabel.setVisible(false);

        String username = usernameTextBox.getText();
        if (username.length() == 0) {
            errorLabel.setText("Please enter the name of the first user");
            errorLabel.setVisible(true);
            valid = false;
        }

        String password = passwordTextBox.getText();
        if (valid && password.length() < 3) {
            valid = false;
            if (password.length() > 0) {
                errorLabel.setText("Password must be at least 3 characters long");
                errorLabel.setVisible(true);
            }
        }

        String confirmation = confirmTextBox.getText();
        if (valid && !confirmation.equals(password)) {
            valid = false;
            if (confirmation.length() > 0) {
                errorLabel.setText("Confirmation must match password");
                errorLabel.setVisible(true);
            }
        }

        nextButton.setVisible(valid);
    }

    @UiHandler("nextButton")
    void onNext(ClickEvent click) {
        String username = usernameTextBox.getText();
        String password = passwordTextBox.getText();

        final User user = new User(username, password);

        userService.addUser(user, SyncCallback.create(new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable throwable) {
                errorLabel.setText(throwable.getLocalizedMessage());
                errorLabel.setVisible(true);
            }

            @Override
            public void onSuccess(Void aVoid) {
                AppEventBus.EVENT_BUS.fireEvent(new AddUserEvent(user));
            }
        }));
    }
}
