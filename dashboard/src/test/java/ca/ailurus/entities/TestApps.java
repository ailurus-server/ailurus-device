package ca.ailurus.entities;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestApps {
    @Test
    public void testSearch() throws Exception {
        App.add("Wordpress", "test", "test", "blog productivity");
        App.add("Minecraft", "test", "test", "games");
        App.add("Owncloud", "test", "test", "productivity");
        App.add("Jenkins", "test", "test", "software");
        App.add("GitList", "test", "test", "software");

        assertEquals(App.search("Software").size(), 2);
        assertEquals(App.search("Productivity").size(), 2);
        assertEquals(App.search("Games").size(), 1);
        assertEquals(App.search("Owncloud").size(), 1);
    }
}