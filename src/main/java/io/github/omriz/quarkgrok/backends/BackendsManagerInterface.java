package io.github.omriz.quarkgrok.backends;

import java.util.List;

public interface BackendsManagerInterface extends BackendInterface {
    public List<BackendInterface> getBackends();
    public void addBackend(BackendInterface backendInterface);
}