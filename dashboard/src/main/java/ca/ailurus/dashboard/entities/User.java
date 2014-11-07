package ca.ailurus.dashboard.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * A database entry for a user
 */
public class User implements Serializable {
    @JsonProperty("username")
    public String name;

    public String password; // TODO: Replace with password hash
    public String email;
}
