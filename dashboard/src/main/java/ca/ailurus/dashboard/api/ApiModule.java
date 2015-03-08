package ca.ailurus.dashboard.api;

import ca.ailurus.dashboard.service.ServiceModule;
import ca.ailurus.dashboard.transaction.TransactionModule;
import com.google.inject.AbstractModule;

public class ApiModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(AppApi.class);
        bind(DeviceApi.class);
        bind(UsersApi.class);
        bind(NetworkApi.class);
        install(new TransactionModule());
        install(new ServiceModule());
    }
}
