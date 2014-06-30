package ca.ailurus.boss.client.firstboot;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;

public class Initialize extends Composite {
    interface InitializeUiBinder extends UiBinder<Widget, Initialize> {
    }

    private static InitializeUiBinder uiBinder = GWT.create(InitializeUiBinder.class);

    @UiField
    FormPanel accountsForm;
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

    public Initialize() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    public HandlerRegistration addClickHandler(ClickHandler handler) {
        return nextButton.addClickHandler(handler);
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
}
