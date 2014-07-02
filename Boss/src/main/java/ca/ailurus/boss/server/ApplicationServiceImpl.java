package ca.ailurus.boss.server;

import ca.ailurus.boss.shared.AppNotFoundException;
import ca.ailurus.boss.shared.Application;
import ca.ailurus.boss.shared.ApplicationService;
import ca.ailurus.boss.shared.Setting;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import javax.servlet.annotation.WebServlet;
import java.util.Map;

@WebServlet("/boss/app")
public class ApplicationServiceImpl extends RemoteServiceServlet implements ApplicationService {

    public ApplicationServiceImpl() {
    }

    @Override
    public Application[] getInstalledApps() {
        try (Storage storage = new Storage()) {
            Map<String, Application> installed = storage.installedApps();
            return installed.values().toArray(new Application[0]);
        }
    }

    @Override
    public Application[] getAvailableApps() {
        try (Storage storage = new Storage()) {
            Map<String, Application> available = storage.availableApps();
            return available.values().toArray(new Application[0]);
        }
    }

    @Override
    public Application[] installApp(String appId) throws AppNotFoundException {
        Storage storage = new Storage();

        try {
            Map<String, Application> installed = storage.installedApps();
            Map<String, Application> available = storage.availableApps();

            if (!available.containsKey(appId)) {
                throw new AppNotFoundException(appId);
            }

            installed.put(appId, available.get(appId));
            available.remove(appId);

            String installCommand = "ai-get install /opt/ailurus/packs/" + appId + ".apkg";
            Process process = Runtime.getRuntime().exec(installCommand);
            int exitStatus = process.waitFor();
            if (exitStatus != 0) {
                // TODO expose this to the client
                throw new RuntimeException("Installation Failed");
            }

            storage.commit();
            return available.values().toArray(new Application[0]);
        } catch (AppNotFoundException exception) {
            storage.rollback();
            throw exception;
        } catch (Exception exception) {
            storage.rollback();
            // TODO handle generic exceptions
            return null;
        }
    }

    @Override
    public boolean uninstallApp(String appId) {
        Storage storage = new Storage();

        try {
            Map<String, Application> installed = storage.installedApps();
            Map<String, Application> available = storage.availableApps();

            if (!installed.containsKey(appId)) {
                return false;
            }

            String installCommand = "ai-get remove /opt/ailurus/packs/" + appId + ".apkg";
            Process process = Runtime.getRuntime().exec(installCommand);
            int exitStatus = process.waitFor();
            if (exitStatus != 0) {
                // TODO expose this to the client
                throw new RuntimeException("Installation Failed");
            }

            available.put(appId, installed.get(appId));
            installed.remove(appId);

            storage.commit();
            return true;
        } catch (Exception exception) {
            storage.rollback();
            return false;
        }
    }

    @Override
    public Setting[] getSettings(String appId) throws AppNotFoundException {
        try (Storage storage = new Storage()) {
            Map<String, Setting[]> settings = storage.settings();
            if (!settings.containsKey(appId)) {
                throw new AppNotFoundException(appId);
            }

            return settings.get(appId);
        }
    }

    @Override
    public void setSettings(String appId, Setting[] newSettings) throws AppNotFoundException {
        Storage storage = new Storage();

        try {
            Map<String, Setting[]> settings = storage.settings();
            if (!settings.containsKey(appId)) {
                throw new AppNotFoundException(appId);
            }

            settings.put(appId, newSettings);
        } catch (AppNotFoundException exception) {
            storage.rollback();
            throw exception;
        } catch (Exception exception) {
            storage.rollback();
            // TODO handle generic exceptions
        }
    }
}
