package ca.ailurus.boss.client.events;

import ca.ailurus.boss.shared.User;
import com.google.web.bindery.event.shared.binder.GenericEvent;

public class AddUserEvent extends GenericEvent {
    private final User user;

    public AddUserEvent(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
