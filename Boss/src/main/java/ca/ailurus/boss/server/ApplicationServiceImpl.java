package ca.ailurus.boss.server;

import ca.ailurus.boss.shared.AppNotFoundException;
import ca.ailurus.boss.shared.Application;
import ca.ailurus.boss.shared.ApplicationService;
import ca.ailurus.boss.shared.Setting;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import javax.servlet.annotation.WebServlet;
import java.util.HashMap;

@WebServlet("/boss/app")
public class ApplicationServiceImpl extends RemoteServiceServlet implements ApplicationService {
    // TODO use a proper database
    HashMap<String, Application> installed = new HashMap<String, Application>();
    HashMap<String, Application> available = new HashMap<String, Application>();
    HashMap<String, Setting[]> settings = new HashMap<String, Setting[]>();

    public ApplicationServiceImpl() {
        installed.put("wordpress", new Application("wordpress", "Wordpress", "An easy to use framework for setting up web pages."));
        available.put("owncloud", new Application("owncloud", "OwnCloud", "A tool for you to manage your own files."));

        Setting[] wordpressSettings = new Setting[2];
        wordpressSettings[0] = new Setting("Something", "SomethingElse");
        wordpressSettings[1] = new Setting("This", "That");

        Setting[] ownCloudSettings = new Setting[10];
        ownCloudSettings[0] = new Setting("Arm", "Chair");
        ownCloudSettings[1] = new Setting("Barn", "Yard");
        ownCloudSettings[2] = new Setting("Cross", "Road");
        ownCloudSettings[3] = new Setting("Drug", "Store");
        ownCloudSettings[4] = new Setting("Ever", "Green");
        ownCloudSettings[5] = new Setting("Four", "Teen");
        ownCloudSettings[6] = new Setting("Grave", "Yard");
        ownCloudSettings[7] = new Setting("Hour", "Glass");
        ownCloudSettings[8] = new Setting("Ice", "Berg");
        ownCloudSettings[9] = new Setting("Jig", "Saw");

        settings.put("wordpress", wordpressSettings);
        settings.put("owncloud", ownCloudSettings);
    }

    @Override
    public Application[] getInstalledApps() {
        return installed.values().toArray(new Application[0]);
    }

    @Override
    public Application[] getAvailableApps() {
        return available.values().toArray(new Application[0]);
    }

    @Override
    public Application[] installApp(String appId) throws AppNotFoundException {
        if (!available.containsKey(appId)) {
            throw new AppNotFoundException(appId);
        }

        installed.put(appId, available.get(appId));
        available.remove(appId);

        return getAvailableApps();
    }

    @Override
    public boolean uninstallApp(String appId) {
        if (!installed.containsKey(appId)) {
            return false;
        }

        available.put(appId, installed.get(appId));
        installed.remove(appId);

        return true;
    }

    @Override
    public Setting[] getSettings(String appId) throws AppNotFoundException {
        if (!settings.containsKey(appId)) {
            throw new AppNotFoundException(appId);
        }

        return settings.get(appId);
    }

    @Override
    public void setSettings(String appId, Setting[] newSettings) throws AppNotFoundException {
        if (!settings.containsKey(appId)) {
            throw new AppNotFoundException(appId);
        }

        settings.put(appId, newSettings);
    }
}
