package ca.ailurus.entities;

import ca.ailurus.database.DatabaseManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;

@DatabaseTable(tableName = "DeviceSettings")
public class DeviceSettings {
    @DatabaseField(defaultValue = "0", id = true) Integer id;
    @DatabaseField(defaultValue = "true") Boolean firstBoot;

    public DeviceSettings() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getFirstBoot() {
        return firstBoot;
    }

    public void setFirstBoot(Boolean firstBoot) {
        this.firstBoot = firstBoot;
    }

    private static DeviceSettings defaultSettings() {
        return new DeviceSettings();
    }

    public static DeviceSettings getSettings() throws SQLException {
        Dao<DeviceSettings, Integer> deviceDao = DatabaseManager.getDeviceDao();
        DeviceSettings settings = deviceDao.queryForId(0);
        if (null == settings) {
            settings = defaultSettings();
            deviceDao.createIfNotExists(settings);
        }
        return settings;
    }
}
