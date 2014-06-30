package ca.ailurus.boss.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Application implements IsSerializable {
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    private String description;
}
