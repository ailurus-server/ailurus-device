package ca.ailurus.dashboard;

import ca.ailurus.dashboard.entities.DeviceSettings;
import ca.ailurus.dashboard.entities.User;
import ca.ailurus.dashboard.mock.MemTransactionMaker;
import ca.ailurus.dashboard.transaction.Transaction;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TransactionTest {
    private MemTransactionMaker txMaker;

    @Before
    public void setup() {
        txMaker = new MemTransactionMaker();
    }

    @Test
    public void shouldAddUsers() {
        try (Transaction tx = txMaker.make()) {
            User user = new User();
            user.name = "John Doe";
            user.email = "johndoe@example.com";
            user.password = "something cool";
            tx.addUser(user);
            tx.commit();
        }

        try (Transaction tx = txMaker.make()) {
            User user = tx.getUser("John Doe");

            assertEquals("John Doe", user.name);
            assertEquals("johndoe@example.com", user.email);
            assertEquals("something cool", user.password);
        }
    }

    @Test
    public void shouldDeleteUsers() {
        try (Transaction tx = txMaker.make()) {
            User user = new User();
            user.name = "John Doe";
            user.email = "johndoe@example.com";
            user.password = "something cool";
            tx.addUser(user);
            tx.commit();
        }

        try (Transaction tx = txMaker.make()) {
            User user = tx.getUser("John Doe");
            assertEquals("John Doe", user.name);
            tx.deleteUser("John Doe");
            tx.commit();
        }

        try (Transaction tx = txMaker.make()) {
            User user = tx.getUser("John Doe");
            assertNull(user);
        }
    }

    @Test
    public void shouldUpdateUsers() {
        try (Transaction tx = txMaker.make()) {
            User user = new User();
            user.name = "John Doe";
            user.email = "johndoe@example.com";
            user.password = "something cool";
            tx.addUser(user);
            tx.commit();
        }

        try (Transaction tx = txMaker.make()) {
            User user = tx.getUser("John Doe");
            user.email = "nobody@example.com";
            tx.setUser(user);
            tx.commit();
        }

        try (Transaction tx = txMaker.make()) {
            User user = tx.getUser("John Doe");
            assertEquals("nobody@example.com", user.email);
            assertEquals("something cool", user.password);
        }
    }

    @Test
    public void shouldPersistSettings() {
        try (Transaction tx = txMaker.make()) {
            DeviceSettings settings = new DeviceSettings();
            settings.url = "abc@example.com";
            tx.createSettings(settings);
            tx.commit();
        }
        try (Transaction tx = txMaker.make()) {
            assertTrue(tx.hasSettings());
            DeviceSettings settings = tx.getSettings();
            assertEquals("abc@example.com", settings.url);
        }
    }
}
