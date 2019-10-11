package io.github.omriz.quarkgrok.backends;

import java.net.URI;
import java.util.Base64;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import io.github.omriz.quarkgrok.structs.QueryResults;

public class OpenGrokBackend implements BackendInterface {
    private URI uri;
    private OpenGrokClientInterface openGrokClientInterface;

    OpenGrokBackend(URI uri) {
        this.uri = uri;
        this.openGrokClientInterface = RestClientBuilder.newBuilder().baseUri(this.uri)
                .build(OpenGrokClientInterface.class);
    }

    public URI getUri() {
        return this.uri;
    }

    private String trimPathPrefix(String path, String prefix) {
        String actual = StringUtils.removeStart(path, "/");
        actual = StringUtils.removeStart(actual, prefix);
        actual = StringUtils.removeStart(actual, "/");
        return actual;
    }

    public String fetchWeb(String path) {
        return this.openGrokClientInterface.getXrefPath(this.trimPathPrefix(path, "xref"));
    }

    public String fetchRaw(String path) {
        return this.openGrokClientInterface.getRawPath(this.trimPathPrefix(path, "raw"));
    }
    public String fetchSuggestConfig() {
        return this.openGrokClientInterface.getSuggestConfig();
    }

    public String UID() {
        return Base64.getEncoder().encodeToString(this.uri.toString().getBytes());
    }

    public QueryResults query(String full, String def, String symbol, String path, String hist, String type) {
        return this.openGrokClientInterface.getQueryResults(full, def, symbol, path, hist, type, "", "");
    }

}