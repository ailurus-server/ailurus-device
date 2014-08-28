package ca.ailurus.dashboard.inject;

import ca.ailurus.dashboard.api.AppApi;
import ca.ailurus.dashboard.api.DeviceApi;
import ca.ailurus.dashboard.api.UserApi;
import com.google.inject.AbstractModule;

public class ApiModule extends AbstractModule {
    public void configure() {
        // http endpoints
        bind(AppApi.class);
        bind(DeviceApi.class);
        bind(UserApi.class);
    }
}
