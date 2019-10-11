package io.github.omriz.quarkgrok;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.github.omriz.quarkgrok.backends.BackendsManagerInterface;

@Path("/xref")
public class XrefFetchResource {

    @Inject
    BackendsManagerInterface backendsManagerInterface;

    @GET
    @Path("/{path: .*}")
    @Produces(MediaType.TEXT_HTML)
    public Response getXrefPath(@PathParam("path") String path) {
        String resp = backendsManagerInterface.fetchWeb(path);
        if (resp == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(resp).build();
    }
}