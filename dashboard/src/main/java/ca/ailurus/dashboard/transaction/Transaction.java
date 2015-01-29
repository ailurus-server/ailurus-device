package ca.ailurus.dashboard.transaction;

import ca.ailurus.dashboard.entities.App;
import ca.ailurus.dashboard.entities.DeviceSettings;
import ca.ailurus.dashboard.entities.UseCase;
import ca.ailurus.dashboard.entities.User;
import ca.ailurus.dashboard.objects.UseCaseCategory;
import org.mapdb.*;

import javax.ws.rs.BadRequestException;
import java.io.Closeable;
import java.util.*;

public class Transaction implements Closeable {
    private static final String DEVICE_SETTINGS = "DEVICE_SETTINGS";
    private static final String INSTALLED_APPS = "INSTALLED_APPS";
    private static final String AVAILABLE_APPS = "AVAILABLE_APPS";
    private static final String USE_CASE = "USE_CASE";
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

    public void addInstalledApp(App app) {
        Map<String, App> apps = db.getTreeMap(INSTALLED_APPS);
        apps.put(app.name, app);
    }

    public List<App> getInstalledApps() {
        Map<String, App> apps = db.getTreeMap(INSTALLED_APPS);

        return new ArrayList<>(apps.values());
    }

    public List<UseCaseCategory> getAllUseCasesSorted() {
        List<UseCaseCategory> useCaseCategories = new ArrayList<>();
        Map<UseCase.Types, List<UseCase>> useCases = db.getTreeMap(USE_CASE);

        for (Map.Entry<UseCase.Types, List<UseCase>> entry : useCases.entrySet()) {
            UseCaseCategory category = new UseCaseCategory();
            category.type = entry.getKey();
            category.useCases = entry.getValue();
            useCaseCategories.add(category);
        }

        Collections.sort(useCaseCategories, new Comparator<UseCaseCategory>() {
            @Override
            public int compare(UseCaseCategory a, UseCaseCategory b) {
                return b.useCases.size() - a.useCases.size();
            }
        });

        return useCaseCategories;
    }

    public void addUseCase(UseCase useCase) {
        Map<UseCase.Types, List<UseCase>> useCases = db.getTreeMap(USE_CASE);
        if (!useCases.containsKey(useCase.type)) {
            useCases.put(useCase.type, new ArrayList<UseCase>());
        }
        useCases.get(useCase.type).add(useCase);
    }

    public UseCase getUseCase(String name) {
        Map<UseCase.Types, List<UseCase>> useCaseMap = db.getTreeMap(USE_CASE);
        for (List<UseCase> useCases : useCaseMap.values()) {
            for (UseCase useCase : useCases) {
                if (useCase.name.equals(name)) {
                    return useCase;
                }
            }
        }
        return null;
    }

    public List<App> appsByUseCase(String useCaseName) {
        Map<String, App> apps = db.getTreeMap(AVAILABLE_APPS);
        List<App> matched = new ArrayList<>();
        for (App app : apps.values()) {
            if (app.tags.contains(useCaseName)) {
                matched.add(app);
            }
        }
        return matched;
    }

    public List<App> searchApps(String keyword) {
        Map<String, App> apps = db.getTreeMap(AVAILABLE_APPS);
        List<App> matched = new ArrayList<>();
        for (App app : apps.values()) {
            if (app.tags.contains(keyword) && app.name.contains(keyword)) {
                matched.add(app);
            }
        }
        return matched;
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
