package ca.ailurus.boss.shared;

import java.io.Serializable;

public class AppNotFoundException extends Exception implements Serializable {
    private String appId;

    public AppNotFoundException() {
    }

    public AppNotFoundException(String appId) {
        super("The application with id \"" + appId + "\" is not available.");
        this.appId = appId;
    }

    public String getAppId() {
        return appId;
    }
}
