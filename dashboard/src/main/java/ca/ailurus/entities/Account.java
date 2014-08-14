package ca.ailurus.entities;


import ca.ailurus.database.DatabaseManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * A database entry or a user
 */
@DatabaseTable(tableName = "Accounts")
public class Account {
    @DatabaseField(id = true) String userName;
    @DatabaseField String password; // TODO: Replace with password hash

    public Account() {

    }

    public Account(String name, String password) {
        this.userName = name;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static Account getAccount(String name) throws SQLException {
        return DatabaseManager.getAccountDao().queryForId(name);
    }

    public static Account createAccount(String name, String password) throws SQLException {
        Account account = new Account(name, password);
        DatabaseManager.getAccountDao().create(account);
        return account;
    }

    // Test stub
    public static void main(String args[]) {
        String databaseUrl = "jdbc:sqlite::memory:";
        try {
            ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrl);
            Dao<Account, String> accountDao = DaoManager.createDao(connectionSource, Account.class);
            TableUtils.createTableIfNotExists(connectionSource, Account.class);

            Account richard = new Account("Richard", "pass");
            accountDao.createIfNotExists(richard);

            Account queried = accountDao.queryForId("Richard");
            System.out.println(queried.getUserName());
            connectionSource.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
