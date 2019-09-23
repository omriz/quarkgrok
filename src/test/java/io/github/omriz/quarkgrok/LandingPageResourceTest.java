package io.github.omriz.quarkgrok;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class LandingPageResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/fe")
          .then()
             .statusCode(200)
             .body(is("hello"));
    }

}