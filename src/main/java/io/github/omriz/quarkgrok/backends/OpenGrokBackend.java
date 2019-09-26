package io.github.omriz.quarkgrok.backends;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Base64;

import org.eclipse.microprofile.rest.client.RestClientBuilder;

import okhttp3.OkHttpClient;
import okhttp3.Request;

import io.github.omriz.quarkgrok.structs.QueryResults;

public class OpenGrokBackend implements BackendInterface {
    private URI uri;
    private OpenGrokClientInterface openGrokClientInterface;
    OkHttpClient client;

    OpenGrokBackend(URI uri) {
        this.uri = uri;
        this.openGrokClientInterface = RestClientBuilder.newBuilder().baseUri(this.uri)
                .build(OpenGrokClientInterface.class);
        this.client = new OkHttpClient();
    }

    public URI getUri() {
        return this.uri;
    };

    public String fetchWeb(String path) throws IOException {
        Request request = new Request.Builder().url(new URL(this.uri.toURL(), path)).build();
        return this.client.newCall(request).execute().body().string();
    }

    public String fetchRaw(String path) throws IOException {
        return this.fetchWeb(path);
    }

    public String UID() {
        return Base64.getEncoder().encodeToString(this.uri.toString().getBytes());
    };

    public QueryResults query(String full, String def, String symbol, String path, String hist, String type) {
        return this.openGrokClientInterface.getQueryResults(full, def, symbol, path, hist, type, "", "", "");
    }

}