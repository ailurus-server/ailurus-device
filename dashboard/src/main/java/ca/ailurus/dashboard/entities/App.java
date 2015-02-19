package ca.ailurus.dashboard.entities;

import java.io.Serializable;

public class App implements Serializable {
    public String name;
    public String description;
    public String imageUrl;
    public String tags;
    public boolean installed;
    public boolean featured;
    public String appUrl;

    public App(String name, String description, String imageUrl, String tags, boolean installed, boolean featured, String appUrl) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.tags = tags;
        this.installed = installed;
        this.featured = featured;
        this.appUrl = appUrl;
    }
}
