package ca.ailurus.boss.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Setting implements IsSerializable {
    public Setting(String name) {
        this(name, null);
    }

    public Setting(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    final private String name;
    final private String value;
}
