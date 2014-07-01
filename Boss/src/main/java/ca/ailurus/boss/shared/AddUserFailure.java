package ca.ailurus.boss.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class AddUserFailure extends Exception implements IsSerializable {
    public AddUserFailure() {}

    public AddUserFailure(String message) {
        super(message);
    }
}
