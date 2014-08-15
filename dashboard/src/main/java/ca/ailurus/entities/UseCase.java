package ca.ailurus.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Arrays;
import java.util.List;

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

    private UseCase(String name, String displayName, String description, Types type) {
        this.name = name;
        this.displayName = displayName;
        this.description = description;
        this.type = type;
    }

    public static List<UseCase> getAll() {
        return Arrays.asList(
            new UseCase("blog", "Blog", " to share your thoughts", Types.Personal),
            new UseCase("profile", "Profile Page", " to showcase your work", Types.Personal),
            new UseCase("game-server", "Game Server", " to host games for your friends", Types.Personal),
            new UseCase("corporate-website", "Corporate Website", " to showcase your company", Types.Business),
            new UseCase("web-server", "Web Server", " to run your own website", Types.Programming),
            new UseCase("source-control", "Source Control", " to safely store your source code", Types.Programming)
        );
    }
}
