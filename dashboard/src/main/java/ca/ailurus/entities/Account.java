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
import java.util.List;

/**
 * A database entry or a user
 */
@DatabaseTable(tableName = "Accounts")
public class Account {
    private static Dao<Account, Integer> dao = DatabaseManager.createDaoFor(Account.class);

    @DatabaseField(generatedId = true) public Integer id;
    @DatabaseField(unique = true) public String userName;
    @DatabaseField public String password; // TODO: Replace with password hash

    public Account() {
    }

    public Account(String name, String password) {
        this.userName = name;
        this.password = password;
    }


    public static Account get(String name) throws SQLException {
        List<Account> accounts = dao.queryForEq("userName", name);
        if (accounts.size() != 1) {
            return null;
        }
        return accounts.get(0);
    }

    public static Account create(String name, String password) throws SQLException {
        Account account = new Account(name, password);
        dao.create(account);
        return account;
    }

    public static void delete(String name) throws SQLException {
        dao.delete(get(name));
    }

    public static void update(String name, String newName, String newPassword) throws SQLException {
        Account account = get(name);
        account.userName = newName;
        account.password = newPassword;
        dao.update(account);
    }
}
