package ca.ailurus.boss.server;

import ca.ailurus.boss.shared.*;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import javax.servlet.annotation.WebServlet;
import java.util.Map;

@WebServlet("/boss/machine")
public class MachineServiceImpl extends RemoteServiceServlet implements MachineService {
    @Override
    public void initialize(User firstUser) throws AlreadyInitializedException {
        Storage storage = new Storage();

        try {
            if (storage.isInitialized()) {
                throw new AlreadyInitializedException();
            }

            Map<String, User> users = storage.users();
            users.put(firstUser.getName(), firstUser);

            Map<String, Application> available = storage.availableApps();
            available.put("wordpress",
                    new Application(
                            "wordpress",
                            "Wordpress",
                            "An easy to use framework for setting up web pages.",
                            "/wordpress"));
            available.put("gitlist",
                    new Application(
                            "gitlist",
                            "gitlist",
                            "An easy to use git manager.",
                            "/gitlist"));

            Map<String, Application> installed = storage.installedApps();
            installed.put("owncloud", new Application(
                    "owncloud",
                    "OwnCloud",
                    "A tool for you to manage your own files.",
                    "/owncloud"));

            Map<String, Setting[]> settings = storage.settings();

            Setting[] wordpressSettings = new Setting[1];
            wordpressSettings[0] = new Setting("URL", "ailurus.tk/wordpress");
            settings.put("wordpress", wordpressSettings);

            Setting[] ownCloudSettings = new Setting[1];
            ownCloudSettings[0] = new Setting("URL", "ailurus.tk/owncloud");
            settings.put("owncloud", ownCloudSettings);

            storage.setInitialized();

            storage.commit();

        } catch (AlreadyInitializedException exception) {
            storage.rollback();
            throw exception;
        } catch (Exception exception) {
            // TODO handle generic exceptions
            storage.rollback();
        }
    }
}
