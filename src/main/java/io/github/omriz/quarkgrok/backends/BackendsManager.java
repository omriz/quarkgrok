package io.github.omriz.quarkgrok.backends;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.github.omriz.quarkgrok.structs.QueryResults;
import io.github.omriz.quarkgrok.structs.ResultLine;
import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
public class BackendsManager implements BackendsManagerInterface {
    private static final Logger LOGGER = Logger.getLogger(BackendsManager.class.getName());
    private ArrayList<BackendInterface> backendInterfaces = new ArrayList<>();
    @ConfigProperty(name = "quarkgrok.backends.manager.address")
    String address;
    private URI uri;
    @ConfigProperty(name = "quarkgrok.backends.manager.servers")
    List<String> backendAddresses;

    // Intializing the manager and backends.
    void onStart(@Observes StartupEvent ev) {
        try {
            uri = new URI(address);
        } catch (URISyntaxException e) {
            LOGGER.warning("Failed to assign address to manager " + address);
        }
        for (String backendAddress : backendAddresses) {
            try {
                addBackend(new OpenGrokBackend(new URI(backendAddress)));
            } catch (URISyntaxException e) {
                LOGGER.warning("Failed to add " + backendAddress);
            }
        }
    }

    public List<BackendInterface> getBackends() {
        return backendInterfaces;
    }

    public void addBackend(BackendInterface backendInterface) {
        backendInterfaces.add(backendInterface);
    }

    public URI getUri() {
        return uri;
    }

    public String fetchWeb(String path) {
        Optional<String> fetchResult = backendInterfaces.parallelStream().map(b -> tryFetchWeb(b, path))
                .filter(s -> s != "").findFirst();
        if (fetchResult.isEmpty()) {
            return "Not Found";
        }
        return fetchResult.get();
    }

    public String fetchRaw(String path) {
        Optional<String> fetchResult = backendInterfaces.parallelStream().map(b -> tryFetchRaw(b, path))
                .filter(s -> s != "").findFirst();
        if (fetchResult.isEmpty()) {
            return "Not Found";
        }
        return fetchResult.get();
    }

    public String UID() {
        return Base64.getEncoder().encodeToString(this.uri.toString().getBytes());
    }

    public QueryResults query(String full, String def, String symbol, String path, String hist, String type) {
        List<QueryResults> queryResultList = backendInterfaces.parallelStream()
                .map(b -> tryQuery(b, full, def, symbol, path, hist, type))//.filter(q -> q != null)
                .collect(Collectors.toList());
        // Combine the results to one.
        QueryResults queryResult = new QueryResults(0, 0, 0, 0, new HashMap<String, List<ResultLine>>());
        for (QueryResults qResults : queryResultList) {
            LOGGER.info(qResults.getTime().toString());
            LOGGER.info(qResults.getResultCount().toString());
            LOGGER.info(qResults.getResults().keySet().toString());
            if (qResults.getTime() > queryResult.getTime()) {
                queryResult.setTime(qResults.getTime());
            }
            queryResult.setResultCount(queryResult.getResultCount() + qResults.getResultCount());
            for (Map.Entry<String, List<ResultLine>> entry : qResults.getResults().entrySet()) {
                if (!queryResult.getResults().containsKey(entry.getKey())) {
                    queryResult.getResults().put(entry.getKey(), new ArrayList<>(entry.getValue()));
                } else {
                    LOGGER.warning("Multiple collected results for " + entry.getKey() + ". Skipping");
                }
            }
        }
        return queryResult;
    }

    public static String tryFetchWeb(BackendInterface backendInterface, String path) {
        try {
            return backendInterface.fetchWeb(path);
        } catch (javax.ws.rs.WebApplicationException e) {
            return "";
        }
    }

    public static String tryFetchRaw(BackendInterface backendInterface, String path) {
        try {
            return backendInterface.fetchRaw(path);
        } catch (javax.ws.rs.WebApplicationException e) {
            return "";
        }
    }

    public static QueryResults tryQuery(BackendInterface backendInterface, String full, String def, String symbol,
            String path, String hist, String type) {
        try {
            return backendInterface.query(full, def, symbol, path, hist, type);
        } catch (javax.ws.rs.WebApplicationException e) {
            return null;
        }
    }
}