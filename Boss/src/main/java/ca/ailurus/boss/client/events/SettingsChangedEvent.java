package ca.ailurus.boss.client.events;

import ca.ailurus.boss.shared.Setting;
import com.google.web.bindery.event.shared.binder.GenericEvent;

public class SettingsChangedEvent extends GenericEvent {
    public SettingsChangedEvent(Setting oldSetting) {
        setting = oldSetting;
    }

    public Setting getSetting() {
        return setting;
    }

    final private Setting setting;

}
