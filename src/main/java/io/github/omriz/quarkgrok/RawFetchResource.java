package io.github.omriz.quarkgrok;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.github.omriz.quarkgrok.backends.BackendsManagerInterface;

@Path("/raw")
public class RawFetchResource {

    @Inject
    BackendsManagerInterface backendsManagerInterface;

    @GET
    @Path("/{path: .*}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getRawPath(@PathParam("path") String path) {
        return backendsManagerInterface.fetchRaw(path);
    }
}