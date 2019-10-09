package io.github.omriz.quarkgrok.backends;

import io.github.omriz.quarkgrok.structs.QueryResults;
import io.quarkus.test.junit.QuarkusTest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;

@QuarkusTest
public class OpenGrokbackendTest {
    private ClientAndServer mockServer;
    private OpenGrokBackend openGrokBackend;

    @BeforeEach
    public void startMockServer() throws URISyntaxException {
        mockServer = startClientAndServer(1080);
        openGrokBackend = new OpenGrokBackend(new URI("http://localhost:1080"));
    }

    @AfterEach
    public void stopMockServer() {
        mockServer.stop();
    }

    @Test
    public void rawfetchTest() {
        mockServer.when(HttpRequest.request().withMethod("GET").withPath("/raw/x.txt")).respond(HttpResponse.response()
                .withStatusCode(200).withBody("Hello World").withHeader("content-type", "text/plain"));
        assertEquals("Hello World", openGrokBackend.fetchRaw("/raw/x.txt"));
    }

    @Test
    public void uidTest() {
        // Encoding for http://localhost:1080
        assertEquals("aHR0cDovL2xvY2FsaG9zdDoxMDgw", openGrokBackend.UID());
    }

    @Test
    public void xreffetchTest() {
        mockServer.when(HttpRequest.request().withMethod("GET").withPath("/xref/x.txt"))
                .respond(HttpResponse.response().withStatusCode(200)
                        .withBody("<html><head></head><body>Hello World</body></html>")
                        .withHeader("content-type", "text/html"));
        Document doc = Jsoup.parse(openGrokBackend.fetchWeb("/xref/x.txt"));
        assertEquals("Hello World", doc.body().text());
    }

    @Test
    public void simpleQueryTest() {
        String response = "{\n" + "\"time\": 13,\n" + "\"resultCount\": 35,\n" + "\"startDocument\": 0,\n"
                + "\"endDocument\": 0,\n" + "\"results\": {\n"
                + "  \"/opengrok/test/org/opensolaris/opengrok/history/hg-export-renamed.txt\": [{\n"
                + "    \"line\": \"# User Vladimir <b>Kotal</b> &lt;Vladimir.<b>Kotal</b>@oracle.com&gt;\",\n"
                + "    \"lineNumber\": \"19\"\n" + "  },{\n"
                + "    \"line\": \"# User Vladimir <b>Kotal</b> &lt;Vladimir.<b>Kotal</b>@oracle.com&gt;\",\n"
                + "    \"lineNumber\":\"29\"\n" + "  }]\n" + "}\n" + "}\n";
        mockServer
                .when(HttpRequest.request().withMethod("GET").withPath("/api/v1/search")
                        .withQueryStringParameter("full", "opengrok"))
                .respond(HttpResponse.response().withStatusCode(200).withBody(response).withHeader("content-type",
                        "application/json"));
        QueryResults results = openGrokBackend.query("opengrok", "", "", "", "", "");
        assertEquals(13, results.getTime().intValue());
        assertEquals(35, results.getResultCount().intValue());
        assertTrue(results.getResults().keySet()
                .contains("/opengrok/test/org/opensolaris/opengrok/history/hg-export-renamed.txt"));
        assertEquals(1, results.getResults().size());
        assertEquals(2, results.getResults()
                .get("/opengrok/test/org/opensolaris/opengrok/history/hg-export-renamed.txt").size());
    }
}
