package ca.ailurus.boss.client.widgets;

import ca.ailurus.boss.client.events.AppEventBus;
import ca.ailurus.boss.shared.Application;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;
import com.google.web.bindery.event.shared.binder.GenericEvent;

public class AppEntry extends Composite {
    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
    @UiField
    Label title;
    @UiField
    Label description;
    @UiField
    Image icon;
    @UiField
    FocusPanel action;
    Application application;
    GenericEvent selectionEvent;
    public AppEntry(Application app, String iconUrl, String actionDescription, GenericEvent event) {
        initWidget(uiBinder.createAndBindUi(this));

        application = app;
        selectionEvent = event;

        String appName = app.getName();
        title.setText(appName);
        description.setText(app.getDescription());
        icon.setUrl("images/apps/" + appName.toLowerCase() + ".png");
        icon.setAltText(appName + " Icon");
    }

    @UiHandler("action")
    void onClick(ClickEvent event) {
        AppEventBus.EVENT_BUS.fireEvent(selectionEvent);
    }

    interface MyUiBinder extends UiBinder<Widget, AppEntry> {
    }
}
