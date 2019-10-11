package io.github.omriz.quarkgrok.backends;

import java.net.URI;

import io.github.omriz.quarkgrok.structs.QueryResults;

public interface BackendInterface {
    public URI getUri();

    public String fetchWeb(String path);

    public String fetchRaw(String path);

    public String fetchSuggestConfig();

    public String UID();

    public QueryResults query(String full, String def, String symbol, String path, String hist, String type);
}