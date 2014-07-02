package ca.ailurus.boss.shared;

import java.io.Serializable;

public class AddUserFailure extends Exception implements Serializable {
    public AddUserFailure() {
    }

    public AddUserFailure(String message) {
        super(message);
    }
}
