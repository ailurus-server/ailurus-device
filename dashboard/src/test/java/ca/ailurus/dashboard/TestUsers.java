package ca.ailurus.dashboard;

import ca.ailurus.entities.User;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TestUsers {
    @Test
    public void testCreate() throws Exception {
        String databaseUrl = "jdbc:sqlite::memory:";
        ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrl);
        Dao<User, Integer> userDao = DaoManager.createDao(connectionSource, User.class);
        TableUtils.createTableIfNotExists(connectionSource, User.class);

        User richard = new User();
        richard.name = "Richard";
        richard.password = "pass";
        userDao.createIfNotExists(richard);

        User queriedById = userDao.queryForId(richard.id);
        assertEquals(queriedById.name, "Richard");
        assertEquals(queriedById.password, "pass");

        List<User> queriedByName = userDao.queryForEq("name", richard.name);
        assertEquals(queriedByName.size(), 1);
        User firstUser = queriedByName.get(0);
        assertEquals(firstUser.name, "Richard");
        assertEquals(firstUser.password, "pass");

        connectionSource.close();
    }
}