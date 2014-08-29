package ca.ailurus.dashboard.managers;

import ca.ailurus.dashboard.entities.User;
import com.j256.ormlite.dao.Dao;

import javax.inject.Singleton;
import java.sql.SQLException;
import java.util.List;

@Singleton
public class UserManagerImpl implements UserManager {
    private Dao<User, Integer> dao = DatabaseManager.createDaoFor(User.class);

    public User get(String name) throws SQLException {
    List<User> users = dao.queryForEq("name", name);
    if (users.size() != 1) {
        return null;
    }
    return users.get(0);
}

    public User create(User user) throws SQLException {
        dao.create(user);
        return user;
    }

    public void delete(String name) throws SQLException {
        dao.delete(get(name));
    }
}
