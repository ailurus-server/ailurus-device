package ca.ailurus.boss.server;

import ca.ailurus.boss.shared.Application;
import ca.ailurus.boss.shared.Setting;
import ca.ailurus.boss.shared.User;
import org.mapdb.Atomic;
import org.mapdb.DB;
import org.mapdb.DBMaker;

import java.io.File;
import java.util.Map;

public class Storage implements AutoCloseable {
    private static String DATABASE_PATH = "temp.db";

    private DB db;

    public Storage() {
        File dbFile = new File(DATABASE_PATH);
        db = DBMaker.newFileDB(dbFile).make();
    }

    public void setInitialized() {
        Atomic.Boolean isInitialized = db.getAtomicBoolean("isInitialized");
        isInitialized.set(true);
    }

    public void setUninitialized() {
        Atomic.Boolean isInitialized = db.getAtomicBoolean("isInitialized");
        isInitialized.set(false);
    }

    public boolean isInitialized() {
        Atomic.Boolean isInitialized = db.getAtomicBoolean("isInitialized");
        return isInitialized.get();
    }

    public Map<String, User> users() {
        return db.getHashMap("users");
    }

    public Map<String, Application> installedApps() {
        return db.getHashMap("installedApps");
    }

    public Map<String, Application> availableApps() {
        return db.getHashMap("availableApps");
    }

    public Map<String, Setting[]> settings() {
        return db.getHashMap("settings");
    }

    public void commit() {
        db.commit();
        db.close();
    }

    public void rollback() {
        db.rollback();
        db.close();
    }

    @Override
    public void close() {
        if (!db.isClosed()) {
            db.close();
        }
    }
}
