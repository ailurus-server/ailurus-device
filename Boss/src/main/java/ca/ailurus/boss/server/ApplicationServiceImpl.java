package ca.ailurus.boss.server;

import ca.ailurus.boss.shared.Application;
import ca.ailurus.boss.shared.ApplicationService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import javax.servlet.annotation.WebServlet;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/boss/app")
public class ApplicationServiceImpl extends RemoteServiceServlet implements ApplicationService {
    // TODO use a proper database
    List<Application> applications = new ArrayList<Application>();

    @Override
    public Application[] getAllApps() {
        return new Application[0];
    }

    @Override
    public void addApp(String appId) {

    }

    @Override
    public boolean removeApp(String appId) {
        return false;
    }
}
