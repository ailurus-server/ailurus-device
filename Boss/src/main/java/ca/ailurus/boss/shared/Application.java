package ca.ailurus.boss.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Application implements IsSerializable {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
