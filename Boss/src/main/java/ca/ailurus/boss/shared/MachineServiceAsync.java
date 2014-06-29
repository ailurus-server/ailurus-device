package ca.ailurus.boss.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MachineServiceAsync {
    public void getHostName(AsyncCallback<String> callback);
    public void setHostName(String hostname, AsyncCallback<Void> callback);
}
