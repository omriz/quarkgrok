package io.github.omriz.quarkgrok.backends;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import io.github.omriz.quarkgrok.structs.QueryResults;

public interface BackendInterface {
    public URI getUri();

    public String fetchWeb(String path);

    public String fetchRaw(String path);

    public String UID();

    public QueryResults query(String full, String def,
            String symbol, String path,String hist,
            String type);
}