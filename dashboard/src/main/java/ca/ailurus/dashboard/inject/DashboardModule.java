package ca.ailurus.dashboard.inject;

import ca.ailurus.dashboard.*;
import com.google.inject.Binder;
import com.google.inject.Module;

public class DashboardModule implements Module {
    public void configure(final Binder binder) {
        // http endpoints
        binder.bind(Welcome.class);
        binder.bind(Dashboard.class);
        binder.bind(AppApi.class);
        binder.bind(DeviceApi.class);
        binder.bind(UserApi.class);
    }
}
