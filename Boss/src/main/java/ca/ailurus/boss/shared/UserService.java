package ca.ailurus.boss.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("user")
public interface UserService extends RemoteService {
    public User login(String username, String passwordHash) throws LoginFailure;

    public void logout(User user);

    public void addUser(User user) throws AddUserFailure;

    public boolean removeUser(String username);
}
