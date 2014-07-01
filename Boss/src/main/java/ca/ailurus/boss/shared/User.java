package ca.ailurus.boss.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class User implements IsSerializable {
    public User() {
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

    private String name;
    private String passwordHash;
}
