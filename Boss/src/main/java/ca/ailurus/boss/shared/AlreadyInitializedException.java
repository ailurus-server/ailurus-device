package ca.ailurus.boss.shared;

import java.io.Serializable;

public class AlreadyInitializedException extends Exception implements Serializable {
    public AlreadyInitializedException() {
        super("The machine has already been initialized");
    }

    public AlreadyInitializedException(String string) {
        super(string);
    }
}
