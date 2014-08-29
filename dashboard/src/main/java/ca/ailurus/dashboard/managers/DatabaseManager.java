package ca.ailurus.dashboard.managers;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseManager {
    final private static String DATABASE_URL = "jdbc:sqlite::memory:";
    // final private static String DATABASE_URL = "jdbc:sqlite:/opt/ailurus/admin/admin.db";

    private static ConnectionSource connectionSource = null;

    private static void initializeDatabase() {
        try {
            connectionSource = new JdbcConnectionSource(DATABASE_URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static {
        initializeDatabase();
    }

    public static <D extends Dao<T,?>,T> D createDaoFor(Class<T> tClass) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, tClass);
            return DaoManager.createDao(connectionSource, tClass);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
