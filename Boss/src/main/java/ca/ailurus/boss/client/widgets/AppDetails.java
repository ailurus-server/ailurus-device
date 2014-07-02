package ca.ailurus.boss.client.widgets;

import ca.ailurus.boss.client.SyncCallback;
import ca.ailurus.boss.client.events.AppEventBus;
import ca.ailurus.boss.client.events.ShowDashboardEvent;
import ca.ailurus.boss.shared.Application;
import ca.ailurus.boss.shared.ApplicationService;
import ca.ailurus.boss.shared.ApplicationServiceAsync;
import ca.ailurus.boss.shared.Setting;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

public class AppDetails extends Composite {
    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
    @UiField
    Label errorLabel;
    @UiField
    Label title;
    @UiField
    Label description;
    @UiField
    Button cancelButton;
    @UiField
    Button updateButton;
    @UiField
    FlexTable settingsTable;
    boolean isValid = true;
    Application application;
    private ApplicationServiceAsync appService = GWT.create(ApplicationService.class);
    public AppDetails(Application app, Setting[] settings) {
        application = app;

        initWidget(uiBinder.createAndBindUi(this));
        title.setText(app.getName());
        description.setText(app.getDescription());
        updateSettings(settings);
    }

    private void updateSettings(Setting[] settings) {
        ChangeHandler handler = new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent changeEvent) {
                validate();
            }
        };

        settingsTable.clear();
        for (int i = 0; i < settings.length; ++i) {
            settingsTable.setText(i, 0, settings[i].getName());
            TextBox valueTextBox = new TextBox();
            valueTextBox.setText(settings[i].getValue());
            valueTextBox.addChangeHandler(handler);
            settingsTable.setWidget(i, 1, valueTextBox);
        }
    }

    private void validate() {
        //TODO validate the settings
    }

    @UiHandler("cancelButton")
    void onCancel(ClickEvent event) {
        AppEventBus.EVENT_BUS.fireEvent(new ShowDashboardEvent());
    }

    @UiHandler("updateButton")
    void onUpdate(ClickEvent event) {
        Setting[] newSettings = new Setting[settingsTable.getRowCount()];
        for (int i = 0; i < newSettings.length; ++i) {
            String settingName = settingsTable.getText(i, 0);
            TextBox settingTextBox = (TextBox) settingsTable.getWidget(i, 1);
            String settingValue = settingTextBox.getValue();
            newSettings[i] = new Setting(settingName, settingValue);
        }

        String appId = application.getId();
        appService.setSettings(appId, newSettings, SyncCallback.create(new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable throwable) {
                // TODO display the error somewhere
            }

            @Override
            public void onSuccess(Void dummy) {
            }
        }));
    }

    interface MyUiBinder extends UiBinder<Widget, AppDetails> {
    }

}
