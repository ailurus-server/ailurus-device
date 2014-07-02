package ca.ailurus.boss.shared;

import java.io.Serializable;

public class Application implements Serializable {
    private String name;
    private String description;
    private String appId;

    public Application() {
    }

    public Application(String appId, String name, String description) {
        this.appId = appId;
        this.name = name;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return appId;
    }

    public void setId(String appId) {
        this.appId = appId;
    }
}
