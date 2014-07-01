package ca.ailurus.boss.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ApplicationServiceAsync {
    public void getInstalledApps(AsyncCallback<Application[]> callback);
    public void getAvailableApps(AsyncCallback<Application[]> callback);
    public void installApp(String appId, AsyncCallback<Application[]> callback);
    public void uninstallApp(String appId, AsyncCallback<Boolean> callback);
    public void getSettings(String appId, AsyncCallback<Setting[]> callback);
    public void setSettings(String appId, Setting[] settings, AsyncCallback<Void> callback);
}
