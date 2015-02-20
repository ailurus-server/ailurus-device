package ca.ailurus.dashboard.transaction;

import ca.ailurus.dashboard.entities.App;
import ca.ailurus.dashboard.entities.DeviceSettings;
import ca.ailurus.dashboard.entities.User;
import org.mapdb.*;

import javax.ws.rs.BadRequestException;
import java.io.Closeable;
import java.util.*;

public class Transaction implements Closeable {
    private static final String DEVICE_SETTINGS = "DEVICE_SETTINGS";
    private static final String APPS = "INSTALLED_APPS";
    private static final String USERS = "USERS";

    private DB db;

    public Transaction(DB db) {
        this.db = db;
    }

    public boolean hasSettings() {
        return !db.getTreeMap(DEVICE_SETTINGS).isEmpty();
    }

    public DeviceSettings getSettings() {
        Map<String, DeviceSettings> settingsMap = db.getTreeMap(DEVICE_SETTINGS);
        if (settingsMap.isEmpty()) {
            return null;
        }
        return settingsMap.get(DEVICE_SETTINGS);
    }

    public void createSettings(DeviceSettings settings) {
        Map<String, DeviceSettings> settingsMap = db.getTreeMap(DEVICE_SETTINGS);
        settingsMap.put(DEVICE_SETTINGS, settings);
    }

    public void addApp(App app) {
        Map<String, App> apps = db.getTreeMap(APPS);
        apps.put(app.name, app);
    }

    public List<App> getInstalledApps() {
        Map<String, App> apps = db.getTreeMap(APPS);

        ArrayList<App> installedApps = new ArrayList<>();
        for (App app: apps.values()) {
            if (app.installed) {
                installedApps.add(app);
            }
        }

        return installedApps;
    }

    public List<App> getFeaturedApps() {
        Map<String, App> apps = db.getTreeMap(APPS);

        ArrayList<App> featuredApps = new ArrayList<>();
        for (App app: apps.values()) {
            if (app.featured) {
                featuredApps.add(app);
            }
        }

        return featuredApps;
    }

    public List<App> searchApps(String keyword) {
        Map<String, App> apps = db.getTreeMap(APPS);
        List<App> matched = new ArrayList<>();
        for (App app : apps.values()) {
            if (containsIgnoreCase(app.tags ,keyword) || containsIgnoreCase(app.name ,keyword)) {
                matched.add(app);
            }
        }
        return matched;
    }

    // taken from http://stackoverflow.com/questions/86780/is-the-contains-method-in-java-lang-string-case-sensitive
    public static boolean containsIgnoreCase(String src, String what) {
        final int length = what.length();
        if (length == 0)
            return true; // Empty string is contained

        final char firstLo = Character.toLowerCase(what.charAt(0));
        final char firstUp = Character.toUpperCase(what.charAt(0));

        for (int i = src.length() - length; i >= 0; i--) {
            // Quick check before calling the more expensive regionMatches() method:
            final char ch = src.charAt(i);
            if (ch != firstLo && ch != firstUp)
                continue;

            if (src.regionMatches(true, i, what, 0, length))
                return true;
        }

        return false;
    }

    public ArrayList<User> listUsers() {
        Map<String, User> users = db.getTreeMap(USERS);
        return new ArrayList<>(users.values());
    }

    public void addUser(User user) {
        Map<String, User> users = db.getTreeMap(USERS);
        if (users.containsKey(user.name)) {
            throw new BadRequestException("The name '" + user.name + "' has already been used.");
        }
        users.put(user.name, user);
    }

    public User getUser(String name) {
        Map<String, User> users = db.getTreeMap(USERS);
        return users.get(name);
    }

    public void setUser(User user) {
        Map<String, User> users = db.getTreeMap(USERS);
        users.put(user.name, user);
    }

    public void deleteUser(String name) {
        Map<String, User> users = db.getTreeMap(USERS);
        users.remove(name);
    }

    public void commit() {
        db.commit();
    }

    @Override
    public void close() {
        db.close();
    }
}
