package ca.ailurus.boss.shared;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String passwordHash;

    public User() {
    }

    public User(User user) {
        this(user.getName(), user.getPasswordHash());
    }

    public User(String name, String passwordHash) {
        this.name = name;
        this.passwordHash = passwordHash;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
