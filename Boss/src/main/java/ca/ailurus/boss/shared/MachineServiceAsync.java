package ca.ailurus.boss.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MachineServiceAsync {
    public void initialize(User user, AsyncCallback<Void> callback);
}
