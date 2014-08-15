package ca.ailurus.entities;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestApps {
    @Test
    public void testSearch() throws Exception {
        App.add("Wordpress", "test", "test", "blog productivity", "test");
        App.add("Minecraft", "test", "test", "games", "test");
        App.add("Owncloud", "test", "test", "productivity", "test");
        App.add("Jenkins", "test", "test", "software", "test");
        App.add("GitList", "test", "test", "software", "test");

        assertEquals(App.search("Software").size(), 2);
        assertEquals(App.search("Productivity").size(), 2);
        assertEquals(App.search("Games").size(), 1);
        assertEquals(App.search("Owncloud").size(), 1);
    }
}