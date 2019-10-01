package io.github.omriz.quarkgrok.backends;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;
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
        mockServer.when(
                HttpRequest.request()
                        .withMethod("GET")
                        .withPath("/raw/x.txt")
        ).respond(
                HttpResponse.response()
                        .withStatusCode(200)
                        .withBody("Hello World")
                        .withHeader("content-type", "text/plain")
        );
        assertEquals("Hello World", openGrokBackend.fetchRaw("/raw/x.txt"));
    }

}
