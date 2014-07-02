package ca.ailurus.boss.shared;

import java.io.Serializable;

public class Application implements Serializable {
    private String name;
    private String description;
    private String id;
    private String url;

    public Application() {
    }

    public Application(String appId, String name, String description, String url) {
        this.id = appId;
        this.name = name;
        this.description = description;
        this.url = url;
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
        return id;
    }

    public void setId(String appId) {
        this.id = appId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
