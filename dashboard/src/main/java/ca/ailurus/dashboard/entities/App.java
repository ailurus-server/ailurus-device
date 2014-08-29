package ca.ailurus.dashboard.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Apps")
public class App {
    @JsonIgnore
    @DatabaseField(generatedId = true) public Integer id;

    @DatabaseField public String name;
    @DatabaseField public String description;
    @DatabaseField public String imageUrl;
    @DatabaseField public String tags;
    @DatabaseField(defaultValue = "false") public boolean installed;
    @DatabaseField public String appUrl;

    public App() {
    }

    public App(String name, String description, String imageUrl, String tags) {
        this(name, description, imageUrl, tags, false, null);
    }

    public App(String name, String description, String imageUrl, String tags, boolean installed, String appUrl) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.tags = tags;
        this.installed = installed;
        this.appUrl = appUrl;
    }
}
