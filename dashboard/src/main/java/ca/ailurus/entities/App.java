package ca.ailurus.entities;

import ca.ailurus.database.DatabaseManager;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;
import java.util.List;

@DatabaseTable(tableName = "Apps")
public class App {
    @JsonIgnore
    @DatabaseField(generatedId = true) public Integer id;

    @DatabaseField public String name;
    @DatabaseField public String description;
    @DatabaseField public String imageUrl;
    @DatabaseField public String tags;
    @DatabaseField(defaultValue = "false") public boolean installed;
    @DatabaseField public String appUrl;

    private static Dao<App, Integer> dao = DatabaseManager.createDaoFor(App.class);

    static {
        try {
            App.addInstalled("Wordpress", "WordPress is web software you can use to create a beautiful website or blog.",
                    "img/apps/wordpress.png", "blog", "/wordpress");
            App.addInstalled("Minecraft", "Minecraft is a popular game about building blocks.",
                    "img/apps/minecraft.png", "game-server", "");
            App.addInstalled("GitList", "GitList is a simple git repository browser.",
                    "img/apps/git.png", "blog source-control", "/gitlist");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public App() {
    }

    public App(String name, String description, String imageUrl, String tags) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.tags = tags;
    }

    public static void add(String name, String description, String imageUrl, String tags) throws SQLException {
        dao.create(new App(name, description, imageUrl, tags));
    }

    public static void addInstalled(String name, String description, String imageUrl, String tags,
                                    String appUrl) throws SQLException {
        App app = new App(name, description, imageUrl, tags);
        app.installed = true;
        app.appUrl = appUrl;
        dao.create(app);
    }

    public static List<App> search(String searchString) throws SQLException {
        String dbSearchString = "%" + searchString.toLowerCase() + "%";
        return dao.queryBuilder().where().like("tags", dbSearchString)
                .or().like("name", dbSearchString).query();
    }

    public static List<App> searchByTag(String tagName) throws SQLException {
        String dbSearchString = "%" + tagName.toLowerCase() + "%";
        return dao.queryBuilder().where().like("tags", dbSearchString).query();
    }

    public static List<App> installed() throws SQLException {
        return dao.queryForEq("installed", true);
    }
}
