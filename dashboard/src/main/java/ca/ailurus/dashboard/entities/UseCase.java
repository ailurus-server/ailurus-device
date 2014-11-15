package ca.ailurus.dashboard.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class UseCase implements Serializable {
    public enum Types {
        Personal,
        Business,
        Programming
    }

    public String name;
    public String displayName;
    public String description;

    @JsonIgnore
    public Types type;

    public UseCase() {
    }

    public UseCase(String name, String displayName, String description, Types type) {
        this.name = name;
        this.displayName = displayName;
        this.description = description;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UseCase useCase = (UseCase) o;

        if (description != null ? !description.equals(useCase.description) : useCase.description != null) return false;
        if (displayName != null ? !displayName.equals(useCase.displayName) : useCase.displayName != null) return false;
        if (name != null ? !name.equals(useCase.name) : useCase.name != null) return false;
        if (type != useCase.type) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (displayName != null ? displayName.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
