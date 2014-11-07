package ca.ailurus.dashboard.api;

import ca.ailurus.dashboard.transaction.TransactionModule;
import com.google.inject.AbstractModule;

public class ApiModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(AppApi.class);
        bind(DeviceApi.class);
        bind(UserApi.class);
        install(new TransactionModule());
    }
}
