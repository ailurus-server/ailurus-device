package ca.ailurus.boss.client.firstboot;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

public class FirstBoot extends Composite {
    public FirstBoot() {
        final FlowPanel panel = new FlowPanel();
        final Welcome welcomeScreen = new Welcome();
        final Initializer initScreen = new Initializer();

        welcomeScreen.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                panel.remove(welcomeScreen);
                panel.add(initScreen);
            }
        });

        panel.addStyleName("expand");
        panel.add(welcomeScreen);
        initWidget(panel);
    }
}
