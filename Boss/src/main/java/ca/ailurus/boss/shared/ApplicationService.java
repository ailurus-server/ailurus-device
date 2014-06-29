package ca.ailurus.boss.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("app")
public interface ApplicationService extends RemoteService {
    public Application[] getAllApps();
    public void addApp(String appId);
    public boolean removeApp(String appId);
}
