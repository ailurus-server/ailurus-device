package ca.ailurus.boss.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("machine")
public interface MachineService extends RemoteService {
    public void initialize(User firstUser) throws AlreadyInitializedException;
}
