package ca.ailurus.dashboard.managers;

import ca.ailurus.dashboard.entities.User;
import com.google.inject.ImplementedBy;

import java.sql.SQLException;

@ImplementedBy(UserManagerImpl.class)
public interface UserManager {
    public User get(String name) throws SQLException;

    public User create(User user) throws SQLException;

    public void delete(String name) throws SQLException;
}
