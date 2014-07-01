package ca.ailurus.boss.client.events;

import ca.ailurus.boss.shared.User;
import com.google.web.bindery.event.shared.binder.GenericEvent;

public class LoginEvent extends GenericEvent {
    public LoginEvent(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    final private User user;
}
