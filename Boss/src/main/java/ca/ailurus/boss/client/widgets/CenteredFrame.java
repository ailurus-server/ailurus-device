package ca.ailurus.boss.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;

public class CenteredFrame extends Composite {
    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
    @UiField
    FlowPanel panel;
    @UiField
    Label titleLabel;
    @UiField
    Image icon;
    public CenteredFrame(String title, String iconUrl) {
        this(title, iconUrl, title + " Icon");
    }

    public CenteredFrame(String title, String iconUrl, String iconAltText) {
        initWidget(uiBinder.createAndBindUi(this));
        titleLabel.setText(title);
        icon.setUrl(iconUrl);
        icon.setAltText(iconAltText);
    }

    public void add(Widget widget) {
        panel.add(widget);
    }

    public void remove(Widget widget) {
        panel.remove(widget);
    }

    interface MyUiBinder extends UiBinder<Widget, CenteredFrame> {
    }


}
