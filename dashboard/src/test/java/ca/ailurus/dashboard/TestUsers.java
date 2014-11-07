package ca.ailurus.dashboard;

import org.junit.Test;

public class TestUsers {
    @Test
    public void testCreate() throws Exception {
        /*String databaseUrl = "jdbc:sqlite::memory:";
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

        connectionSource.close();*/
    }
}