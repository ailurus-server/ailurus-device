package ca.ailurus.dashboard.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * A database entry for a user
 */
@DatabaseTable(tableName = "Accounts")
public class User {
    @JsonIgnore
    @DatabaseField(generatedId = true) public Integer id;

    @JsonProperty("username")
    @DatabaseField(unique = true) public String name;

    @DatabaseField public String password; // TODO: Replace with password hash
    @DatabaseField public String email;
}
