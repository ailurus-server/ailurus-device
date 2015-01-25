package ca.ailurus.dashboard.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.body.MultipartBody;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Dns4eDomainService implements DomainService {
    private final static Log log = LogFactory.getLog(Dns4eDomainService.class);

    private final static String DNS_USER = "6a0c97f49ec7451f83ca";
    private final static String DNS_KEY = "QHWKhiNVs7DrLsDZ2skZOSGGSwIn3BUF3jZMOEDw";
    private final static int TTL_SECONDS = 60 * 60;

    public boolean recordExists(String subDomain) {
        List<String> records = getRecords(subDomain);
        return records != null && !records.isEmpty();
    }

    @Override
    public List<String> getRecords(String subDomain) {
        log.info("Record get request for " + subDomain);
        HttpRequest request = Unirest.get(urlForDomain(subDomain));
        List<String> result = new ArrayList<>();
        try {
            HttpResponse<JsonNode> response = getAuthedResponse(request);
            JSONObject body = response.getBody().getObject();
            if (checkSuccessful(response) && body.has("data")) {
                JSONArray records = body.getJSONObject("data").getJSONObject("A").getJSONArray("records");
                for (int i = 0; i < records.length(); i++) {
                    result.add(records.getString(i));
                }
                return result;
            } else {
                return null;
            }
        } catch (UnirestException e) {
            throw new RuntimeException("Exception caused: " + e.getMessage());
        }
    }

    // Adds a record with automatic IP address detection
    @Override
    public Status addRecord(String subDomain) {
        return addRecord(subDomain, null);
    }

    @Override
    public Status addRecord(String subDomain, String ipAddress) {
        if (recordExists(subDomain)) return Status.ENTRY_EXISTS;
        return updateRecord(subDomain, ipAddress);
    }

    @Override
    public Status updateRecord(String subDomain) {
        return updateRecord(subDomain, null);
    }

    @Override
    public Status updateRecord(String subDomain, String ipAddress) {
        log.info("Record update request for " + subDomain + "," + ipAddress);
        MultipartBody request = Unirest.post(urlForDomain(subDomain))
                .basicAuth(DNS_USER, DNS_KEY)
                .field("ttl", TTL_SECONDS);
        if (ipAddress != null) {
            request = request.field("record", ipAddress);
        }
        try {
            HttpResponse<JsonNode> response = request.asJson();
            if (checkSuccessful(response)) {
                return Status.OK;
            }
        } catch (UnirestException e) {
            log.error(e);
        }
        return Status.FAILED;
    }

    @Override
    public Status deleteRecord(String subDomain) {
        log.info("Record delete request for " + subDomain);
        HttpRequest request = Unirest.delete(urlForDomain(subDomain));
        try {
            HttpResponse<JsonNode> response = getAuthedResponse(request);
            if (checkSuccessful(response)) {
                return Status.OK;
            }
        } catch (UnirestException e) {
            log.error(e);
        }
        return Status.FAILED;
    }

    private String urlForDomain(String domain) {
        return "https://api.dns4e.com/v7/" + domain + ".ailurus.ca/a";
    }

    private HttpResponse<JsonNode> getAuthedResponse(HttpRequest request) throws UnirestException {
        return request.basicAuth(DNS_USER, DNS_KEY).asJson();
    }

    private boolean checkSuccessful(HttpResponse response) {
        int statusCode = response.getStatus();
        boolean successful = statusCode >= 200 && statusCode < 300;
        if (!successful) {
            log.warn("Request not successful: " + response.getStatusText());
        }
        return statusCode >= 200 && statusCode < 300;
    }
}
