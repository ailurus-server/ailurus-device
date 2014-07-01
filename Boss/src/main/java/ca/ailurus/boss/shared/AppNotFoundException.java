package ca.ailurus.boss.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class AppNotFoundException extends Exception implements IsSerializable {
    public AppNotFoundException() {
    }

    public AppNotFoundException(String appId) {
        super("The application with id \"" + appId + "\" is available.");
        this.appId = appId;
    }

    public String getAppId() {
        return appId;
    }

    private String appId;
}
