package io.github.omriz.quarkgrok;

import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import io.github.omriz.quarkgrok.backends.BackendsManagerInterface;
import io.github.omriz.quarkgrok.structs.QueryResults;

@Path("/query")
public class QueryResultResource {

    @Inject
    BackendsManagerInterface backendsManagerInterface;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public QueryResults getQueryResults(@DefaultValue("") @QueryParam("full") String full,
            @DefaultValue("") @QueryParam("def") String def, @DefaultValue("") @QueryParam("symbol") String symbol,
            @DefaultValue("") @QueryParam("path") String path, @DefaultValue("") @QueryParam("hist") String hist,
            @DefaultValue("") @QueryParam("type") String type,
            @DefaultValue("") @QueryParam("maxresults") String maxresults,
            @DefaultValue("") @QueryParam("start") String start) {
        return backendsManagerInterface.query(full, def, symbol, path, hist, type);
    }
}