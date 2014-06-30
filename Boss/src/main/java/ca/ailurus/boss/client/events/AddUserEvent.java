package ca.ailurus.boss.client.events;

import com.google.web.bindery.event.shared.binder.GenericEvent;

public class AddUserEvent extends GenericEvent {
    public AddUserEvent(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    private final String username;
    private final String password;
}
