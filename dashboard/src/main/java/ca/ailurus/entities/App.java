package ca.ailurus.entities;

import ca.ailurus.database.DatabaseManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;
import java.util.List;

@DatabaseTable(tableName = "Apps")
public class App {
    @DatabaseField(generatedId = true) public Integer id;
    @DatabaseField public String name;
    @DatabaseField public String description;
    @DatabaseField public String imageUrl;
    @DatabaseField public String tags;

    private static Dao<App, Integer> dao = DatabaseManager.createDaoFor(App.class);

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

    public static List<App> search(String searchString) throws SQLException {
        String dbSearchString = "%" + searchString.toLowerCase() + "%";
        return dao.queryBuilder().where().like("tags", dbSearchString)
                .or().like("name", dbSearchString).query();
    }
}
