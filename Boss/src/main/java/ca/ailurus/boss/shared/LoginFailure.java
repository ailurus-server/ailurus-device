package ca.ailurus.boss.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class LoginFailure extends Exception implements IsSerializable {
    public LoginFailure() {}

    public LoginFailure(String message) {
        super(message);
    }
}
