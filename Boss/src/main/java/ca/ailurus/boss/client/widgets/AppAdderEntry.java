package ca.ailurus.boss.client.widgets;

import ca.ailurus.boss.client.events.AddAppRequestEvent;
import ca.ailurus.boss.client.events.AppEventBus;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Widget;

public class AppAdderEntry  extends Composite {
    interface MyUiBinder extends UiBinder<Widget, AppAdderEntry> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    @UiField
    FocusPanel panel;

    public AppAdderEntry() {
        initWidget(uiBinder.createAndBindUi(this));
    }


    @UiHandler("panel")
    void onClick(ClickEvent event) {
        AppEventBus.EVENT_BUS.fireEvent(new AddAppRequestEvent());
    }

}
