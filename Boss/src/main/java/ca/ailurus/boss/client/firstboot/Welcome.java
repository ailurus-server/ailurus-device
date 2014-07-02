package ca.ailurus.boss.client.firstboot;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Widget;

public class Welcome extends Composite {
    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
    @UiField
    FocusPanel splash;

    public Welcome() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    public HandlerRegistration addClickHandler(ClickHandler handler) {
        return splash.addClickHandler(handler);
    }

    interface MyUiBinder extends UiBinder<Widget, Welcome> {
    }
}
