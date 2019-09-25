package io.github.omriz.quarkgrok.backends;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import io.github.omriz.quarkgrok.structs.QueryResults;

@Path("/api/v1")
@RegisterRestClient
public interface OpenGrokClientInterface {
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    QueryResults getQueryResults(@QueryParam("full") String full, @QueryParam("def") String def,
            @QueryParam("symbol") String symbol, @QueryParam("path") String path, @QueryParam("hist") String hist,
            @QueryParam("type") String type, @QueryParam("projects") String projects,
            @QueryParam("maxresults") String maxresults, @QueryParam("start") String start);
}