package ca.ailurus.boss.shared;

import java.io.Serializable;

public class LoginFailure extends Exception implements Serializable {
    public LoginFailure() {
    }

    public LoginFailure(String message) {
        super(message);
    }
}
