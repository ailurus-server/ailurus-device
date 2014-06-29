package ca.ailurus.boss.server;

import ca.ailurus.boss.shared.User;
import ca.ailurus.boss.shared.UserService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import javax.servlet.annotation.WebServlet;
import java.util.ArrayList;
import java.util.List;


@WebServlet("/boss/user")
public class UserServiceImpl extends RemoteServiceServlet implements UserService {
    // TODO use a proper database
    List<User> users = new ArrayList<User>();

    @Override
    public User login(String username, String passwordHash) {
        if (users.isEmpty()) {
            return null;
        }

        User user = new User();
        user.setName(username);
        user.setPasswordHash(passwordHash);

        return user;
    }

    @Override
    public void addUser(User user) {
        int index = findUserByName(user.getName());
        if (index >= 0) {
            // TODO throw some error
        }

        users.add(user);
    }

    @Override
    public boolean removeUser(String username) {
        int index = findUserByName(username);
        if (index < 0) {
            return false;
        }

        users.remove(index);
        return true;
    }

    private int findUserByName(String username) {
        for (int i = 0; i < users.size(); ++i) {
            if (users.get(i).getName().equals(username)) {
                return i;
            }
        }
        return -1;
    }
}
