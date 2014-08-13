package ca.ailurus.database;

import ca.ailurus.entities.Account;
import ca.ailurus.entities.DeviceSettings;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseManager {
    final private static String DATABASE_URL = "jdbc:sqlite:/opt/ailurus/admin/admin.db";
    private static ConnectionSource connectionSource = null;

    private static Dao<Account, String> accountDao;
    private static Dao<DeviceSettings, Integer> deviceDao;

    private static void initializeDatabase() {
        try {
            connectionSource = new JdbcConnectionSource(DATABASE_URL);
            TableUtils.createTableIfNotExists(connectionSource, Account.class);
            TableUtils.createTableIfNotExists(connectionSource, DeviceSettings.class);

            accountDao = DaoManager.createDao(connectionSource, Account.class);
            deviceDao = DaoManager.createDao(connectionSource, DeviceSettings.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static {
        initializeDatabase();
    }

    public static ConnectionSource getConnectionSource() {
        return connectionSource;
    }

    public static Dao<Account, String> getAccountDao() {
        return accountDao;
    }

    public static Dao<DeviceSettings, Integer> getDeviceDao() {
        return deviceDao;
    }
}
