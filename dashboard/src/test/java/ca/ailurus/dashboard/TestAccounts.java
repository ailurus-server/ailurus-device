package ca.ailurus.dashboard;

import ca.ailurus.entities.Account;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestAccounts {
    @Test
    public void testCreate() throws Exception {
        String databaseUrl = "jdbc:sqlite::memory:";
        ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrl);
        Dao<Account, String> accountDao = DaoManager.createDao(connectionSource, Account.class);
        TableUtils.createTableIfNotExists(connectionSource, Account.class);

        Account richard = new Account("Richard", "pass");
        accountDao.createIfNotExists(richard);

        Account queried = accountDao.queryForId("Richard");
        assertEquals(queried.userName, "Richard");
        assertEquals(queried.password, "pass");

        connectionSource.close();
    }
}