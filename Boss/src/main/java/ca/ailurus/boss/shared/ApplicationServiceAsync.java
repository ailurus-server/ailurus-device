package ca.ailurus.boss.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ApplicationServiceAsync {
    public void getAllApps(AsyncCallback<Application[]> callback);
    public void addApp(String appId, AsyncCallback<Void> callback);
    public void removeApp(String appId, AsyncCallback<Boolean> callback);
}
