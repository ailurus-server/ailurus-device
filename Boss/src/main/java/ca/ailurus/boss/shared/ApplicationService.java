package ca.ailurus.boss.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("app")
public interface ApplicationService extends RemoteService {
    public Application[] getInstalledApps();

    public Application[] getAvailableApps();

    public Application[] installApp(String appId) throws AppNotFoundException;

    public boolean uninstallApp(String appId);

    public Setting[] getSettings(String appId) throws AppNotFoundException;

    public void setSettings(String appId, Setting[] settings) throws AppNotFoundException;

}
