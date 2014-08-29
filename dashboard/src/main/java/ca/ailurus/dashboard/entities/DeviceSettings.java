package ca.ailurus.dashboard.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "DeviceSettings")
public class DeviceSettings {
    @DatabaseField(id = true) public int id = 0;
    @DatabaseField public boolean initialized = false;
    @DatabaseField public String url = null;

}
