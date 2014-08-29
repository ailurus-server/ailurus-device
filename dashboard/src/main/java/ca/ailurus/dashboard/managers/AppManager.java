package ca.ailurus.dashboard.managers;

import ca.ailurus.dashboard.entities.App;
import com.google.inject.ImplementedBy;

import java.sql.SQLException;
import java.util.List;

@ImplementedBy(AppManagerImpl.class)
public interface AppManager {
    void add(App app) throws SQLException;

    List<App> search(String searchString) throws SQLException;

    List<App> searchByTag(String tagName) throws SQLException;

    List<App> installed() throws SQLException;
}
