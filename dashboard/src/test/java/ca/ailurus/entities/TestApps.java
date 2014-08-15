package ca.ailurus.entities;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestApps {
    @Test
    public void testSearch() throws Exception {
        App.add("aaa", "test", "test", "tag-1");
        App.add("bbb", "test", "test", "tag-2 tag-1");
        App.add("ccc", "test", "test", "tag-3");
        App.add("ddd", "test", "test", "tag-3");

        assertEquals(App.search("tag-1").size(), 2);
        assertEquals(App.search("tag-2").size(), 1);
        assertEquals(App.search("bbb").size(), 1);
        assertEquals(App.search("tag-3").size(), 2);
    }
}