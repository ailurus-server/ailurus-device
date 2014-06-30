package ca.ailurus.boss.client.events;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.web.bindery.event.shared.EventBus;

public class AppEventBus {
    public static EventBus EVENT_BUS = GWT.create(SimpleEventBus.class);
}
