package ca.ailurus.boss.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserServiceAsync {
    public void login(String username, String passwordHash, AsyncCallback<User> callback);
    public void addUser(User user, AsyncCallback<Void> callback);
    public void removeUser(String username, AsyncCallback<Boolean> callback);
}
