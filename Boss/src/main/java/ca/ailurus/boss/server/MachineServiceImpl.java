package ca.ailurus.boss.server;

import ca.ailurus.boss.shared.MachineService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import javax.servlet.annotation.WebServlet;
import java.net.InetAddress;
import java.net.UnknownHostException;

@WebServlet("/boss/machine")
public class MachineServiceImpl extends RemoteServiceServlet implements MachineService {
    @Override
    public String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException exception) {
            return HOSTNAME_UNKNOWN;
        }
    }

    @Override
    public void setHostName(String hostname) {

    }
}
