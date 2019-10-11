package io.github.omriz.quarkgrok.backends;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import io.github.omriz.quarkgrok.structs.QueryResults;

// Atempting to implement a subset of opengrok's web api:
// https://github.com/oracle/opengrok/wiki/Web-services
// https://opengrok.docs.apiary.io/#
// This also includes the fetching of reference (xref) and raw data(raw).
@Path("/")
@RegisterRestClient
public interface OpenGrokClientInterface {
    // Note: We are deliberitly emitting the projects paramater to search all
    // projects.
    @GET
    @Path("/api/v1/search")
    @Produces(MediaType.APPLICATION_JSON)
    QueryResults getQueryResults(@QueryParam("full") String full, @QueryParam("def") String def,
            @QueryParam("symbol") String symbol, @QueryParam("path") String path, @QueryParam("hist") String hist,
            @QueryParam("type") String type, @QueryParam("maxresults") String maxresults,
            @QueryParam("start") String start);

    @GET
    @Path("/xref/{path: .*}")
    @Produces(MediaType.TEXT_HTML)
    String getXrefPath(@PathParam("path") String path);

    @GET
    @Path("/raw/{path: .*}")
    @Produces(MediaType.TEXT_PLAIN)
    String getRawPath(@PathParam("path") String path);
}