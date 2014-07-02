package ca.ailurus.boss.server;

import ca.ailurus.boss.shared.AddUserFailure;
import ca.ailurus.boss.shared.LoginFailure;
import ca.ailurus.boss.shared.User;
import ca.ailurus.boss.shared.UserService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import javax.servlet.annotation.WebServlet;
import java.util.Map;


@WebServlet("/boss/user")
public class UserServiceImpl extends RemoteServiceServlet implements UserService {
    @Override
    public User login(String username, String passwordHash) throws LoginFailure {
        try (Storage storage = new Storage()) {
            Map<String, User> users = storage.users();

            if (!users.containsKey(username)) {
                throw new LoginFailure("User does not exist");
            }

            User user = new User(users.get(username));
            if (!user.getPasswordHash().equals(passwordHash)) {
                throw new LoginFailure("Password is incorrect");
            }

            return user;
        }
    }

    @Override
    public void logout(User user) {
        // TODO invalidate user session
    }

    @Override
    public void addUser(User user) throws AddUserFailure {
        Storage storage = new Storage();

        try {
            Map<String, User> users = storage.users();

            if (users.containsKey(user.getName())) {
                throw new AddUserFailure("Username has already been taken.");
            }
            users.put(user.getName(), user);
            storage.commit();
        } catch (AddUserFailure exception) {
            storage.rollback();
            throw exception;
        } catch (Exception exception) {
            storage.rollback();
            // TODO handle generic exceptions
        }

    }

    @Override
    public boolean removeUser(String username) {
        Storage storage = new Storage();

        try {
            Map<String, User> users = storage.users();

            if (!users.containsKey(username)) {
                return false;
            }

            users.remove(username);
            storage.commit();
            return true;
        } catch (Exception exception) {
            storage.rollback();
            // TODO handle generic exceptions
            return false;
        }
    }
}
