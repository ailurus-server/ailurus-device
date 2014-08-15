package ca.ailurus.entities;


import ca.ailurus.database.DatabaseManager;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;
import java.util.List;

/**
 * A database entry for a user
 */
@DatabaseTable(tableName = "Accounts")
public class User {
    private static Dao<User, Integer> dao = DatabaseManager.createDaoFor(User.class);

    @JsonIgnore
    @DatabaseField(generatedId = true) public Integer id;

    @JsonProperty("username")
    @DatabaseField(unique = true) public String name;

    @DatabaseField public String password; // TODO: Replace with password hash
    @DatabaseField public String email;

    public static User get(String name) throws SQLException {
        List<User> users = dao.queryForEq("name", name);
        if (users.size() != 1) {
            return null;
        }
        return users.get(0);
    }

    public static User create(User user) throws SQLException {
        dao.create(user);
        return user;
    }

    public static void delete(String name) throws SQLException {
        dao.delete(get(name));
    }
}
