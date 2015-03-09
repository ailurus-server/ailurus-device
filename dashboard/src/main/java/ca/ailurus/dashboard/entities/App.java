package ca.ailurus.dashboard.entities;

import java.io.Serializable;

public class App implements Serializable {
    public String displayName;
    public String name;
    public String description;
    public String imageUrl;
    public String tags;
    public boolean installed;
    public int progress;
    public boolean featured;
    public String appUrl;

    public App(String displayName, String name, String description, String imageUrl, String tags, boolean installed,
               int progress, boolean featured, String appUrl) {
        this.displayName = displayName;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.tags = tags;
        this.installed = installed;
        this.progress = progress;
        this.featured = featured;
        this.appUrl = appUrl;
    }
}
