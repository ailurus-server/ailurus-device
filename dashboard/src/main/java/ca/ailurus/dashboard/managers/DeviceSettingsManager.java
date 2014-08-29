package ca.ailurus.dashboard.managers;

import ca.ailurus.dashboard.entities.DeviceSettings;
import com.google.inject.ImplementedBy;

import java.sql.SQLException;

@ImplementedBy(DeviceSettingsManagerImpl.class)
public interface DeviceSettingsManager {

    public DeviceSettings defaultSettings();

    public DeviceSettings getSettings() throws SQLException;

    public void update(DeviceSettings settings) throws SQLException;
}
