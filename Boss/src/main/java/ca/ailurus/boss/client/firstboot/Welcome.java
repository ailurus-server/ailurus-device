package ca.ailurus.boss.client.firstboot;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;

public class Welcome extends Composite {
    interface WelcomeUiBinder extends UiBinder<Widget, Welcome> {
    }

    private static WelcomeUiBinder uiBinder = GWT.create(WelcomeUiBinder.class);

    @UiField
    FocusPanel splash;

    public Welcome() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    public HandlerRegistration addClickHandler(ClickHandler handler) {
        return splash.addClickHandler(handler);
    }
}