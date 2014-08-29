package ca.ailurus.dashboard.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UseCase {
    public enum Types {
        Personal,
        Business,
        Programming
    }

    public String name;
    public String displayName;
    public String description;

    @JsonIgnore
    public Types type;

    public UseCase() {
    }

    public UseCase(String name, String displayName, String description, Types type) {
        this.name = name;
        this.displayName = displayName;
        this.description = description;
        this.type = type;
    }
}
