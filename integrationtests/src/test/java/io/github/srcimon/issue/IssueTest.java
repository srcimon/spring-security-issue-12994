package io.github.srcimon.issue;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;
import io.restassured.filter.cookie.CookieFilter;

class IssueTest {

    private static final String SESSION_NEW_API_URL = "http://localhost:8080/api/is-session-new";

    @Test
    void oneUser_multipleRequests_reusesSession() {
        CookieFilter cookieFilter = new CookieFilter();
        given()
                .filter(cookieFilter)
                .when().get(SESSION_NEW_API_URL)
                .then()
                .statusCode(200)
                .body(is("is new session"));

        given()
                .filter(cookieFilter)
                .when().get(SESSION_NEW_API_URL)
                .then()
                .statusCode(200)
                .body(is("is existing session"));
    }
}
