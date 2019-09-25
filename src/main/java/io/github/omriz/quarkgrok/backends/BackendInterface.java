package io.github.omriz.quarkgrok.backends;

import java.net.URL;

public interface BackendInterface {
    public URL getUrl();

    public String fetchWeb(String path);

    public String fetchRaw(String path);

    public String UID();
}