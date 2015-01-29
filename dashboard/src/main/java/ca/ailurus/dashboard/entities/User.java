package ca.ailurus.dashboard.entities;

import java.io.Serializable;

/**
 * A database entry for a user
 */
public class User implements Serializable {
    public String name;
    public String password; // TODO: Replace with password hash
    public String email;
}
