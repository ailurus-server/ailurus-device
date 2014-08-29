package ca.ailurus.dashboard.managers;

import ca.ailurus.dashboard.entities.App;
import com.j256.ormlite.dao.Dao;

import javax.inject.Singleton;
import java.sql.SQLException;
import java.util.List;

@Singleton
public class AppManagerImpl implements AppManager {
    private static Dao<App, Integer> dao = DatabaseManager.createDaoFor(App.class);

    public AppManagerImpl() {
        try {
            add(new App("Wordpress",
                        "WordPress is web software you can use to create a beautiful website or blog.",
                        "img/apps/wordpress.png",
                        "blog",
                        true,
                        "/wordpress"));
            add(new App("Minecraft",
                        "Minecraft is a popular game about building blocks.",
                        "img/apps/minecraft.png",
                        "game-server",
                        true,
                        ""));
            add(new App("GitList",
                        "GitList is a simple git repository browser.",
                        "img/apps/git.png",
                        "blog source-control",
                        true,
                        "/gitlist"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(App app) throws SQLException {
        dao.create(app);
    }

    @Override
    public List<App> search(String searchString) throws SQLException {
        String dbSearchString = "%" + searchString.toLowerCase() + "%";
        return dao.queryBuilder().where().like("tags", dbSearchString)
                .or().like("name", dbSearchString).query();
    }

    @Override
    public List<App> searchByTag(String tagName) throws SQLException {
        String dbSearchString = "%" + tagName.toLowerCase() + "%";
        return dao.queryBuilder().where().like("tags", dbSearchString).query();
    }

    @Override
    public List<App> installed() throws SQLException {
        return dao.queryForEq("installed", true);
    }
}
