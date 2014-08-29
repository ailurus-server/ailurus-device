package ca.ailurus.dashboard.managers;

import com.google.inject.AbstractModule;

public class ManagerModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(UseCaseManager.class).to(UseCaseManagerImpl.class);
        bind(DeviceSettingsManager.class).to(DeviceSettingsManagerImpl.class);
        bind(UserManager.class).to(UserManagerImpl.class);
    }
}
