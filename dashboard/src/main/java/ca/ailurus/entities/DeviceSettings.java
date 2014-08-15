package ca.ailurus.entities;

import ca.ailurus.database.DatabaseManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;

@DatabaseTable(tableName = "DeviceSettings")
public class DeviceSettings {
    private static Dao<DeviceSettings, Integer> dao =
            DatabaseManager.createDaoFor(DeviceSettings.class);

    @DatabaseField(id = true) public int id = 0;
    @DatabaseField public boolean initialized = false;

    private static DeviceSettings defaultSettings() {
        return new DeviceSettings();
    }

    public static DeviceSettings getSettings() throws SQLException {
        DeviceSettings settings = dao.queryForId(0);
        if (null == settings) {
            settings = defaultSettings();
            dao.createIfNotExists(settings);
        }
        return settings;
    }

    public static void update(DeviceSettings settings) throws SQLException {
        dao.update(settings);
    }
}
