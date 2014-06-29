package ca.ailurus.boss.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("machine")
public interface MachineService extends RemoteService {
    public static final String HOSTNAME_UNKNOWN = "Hostname Unknown";

    public String getHostName();
    public void setHostName(String hostname);
}
