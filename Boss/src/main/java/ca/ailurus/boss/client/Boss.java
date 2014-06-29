package ca.ailurus.boss.client;

import ca.ailurus.boss.shared.MachineService;
import ca.ailurus.boss.shared.MachineServiceAsync;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;

public class Boss implements EntryPoint {
    private MachineServiceAsync bossService = GWT.create(MachineService.class);

    @Override
    public void onModuleLoad() {
        Button b = new Button("ClickMe", new ClickHandler() {
            public void onClick(ClickEvent event) {
                showHostname();
            }
        });

        RootPanel.get().add(b);
    }

    private void showHostname() {
        AsyncCallback<String> callback = new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert(throwable.getMessage());
            }

            @Override
            public void onSuccess(String hostname) {
                Window.alert(hostname);
            }
        };

        bossService.getHostName(callback);
    }
}
