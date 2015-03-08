package ca.ailurus.dashboard.service;

import java.util.List;

public interface DomainService {
    public enum Status {
        OK,
        ENTRY_EXISTS,
        FAILED
    }
    public boolean recordExists(String subDomain);
    public List<String> getRecords(String subDomain);
    public Status addRecord(String subDomain);
    public Status addRecord(String subDomain, String ipAddress);
    public Status updateRecord(String subDomain);
    public Status updateRecord(String subDomain, String ipAddress);
    public Status deleteRecord(String subDomain);
}
