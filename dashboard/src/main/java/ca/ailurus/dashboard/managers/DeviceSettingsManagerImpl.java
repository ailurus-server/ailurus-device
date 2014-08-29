package ca.ailurus.dashboard.managers;

import ca.ailurus.dashboard.entities.DeviceSettings;
import com.j256.ormlite.dao.Dao;

import javax.inject.Singleton;
import java.sql.SQLException;

@Singleton
public class DeviceSettingsManagerImpl implements DeviceSettingsManager {
    private static Dao<DeviceSettings, Integer> dao =
            DatabaseManager.createDaoFor(DeviceSettings.class);

    public DeviceSettings defaultSettings() {
        return new DeviceSettings();
    }

    public DeviceSettings getSettings() throws SQLException {
        DeviceSettings settings = dao.queryForId(0);
        if (null == settings) {
            settings = defaultSettings();
            dao.createIfNotExists(settings);
        }
        return settings;
    }

    public void update(DeviceSettings settings) throws SQLException {
        dao.update(settings);
    }
}
