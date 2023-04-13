package io.github.srcimon.issue;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;
import io.restassured.filter.cookie.CookieFilter;

class SessionBehaviourTest {

    private static final String SESSION_NEW_API_URL = "http://localhost:8080/api/is-session-new";

    @Test
    void oneUser_unknownUser_notAuthenticated() {
        given()
                .auth().basic("unknown-user", "pass3")
                .when().get(SESSION_NEW_API_URL)
                .then()
                .statusCode(401);
    }

    @Test
    void oneUser_multipleRequests_reusesSession() {
        CookieFilter cookieFilter = new CookieFilter();

        given()
                .auth().basic("user1", "pass1")
                .filter(cookieFilter)
                .when().get(SESSION_NEW_API_URL)
                .then()
                .statusCode(200)
                .body(is("is new session"));

        given()
                .auth().basic("user1", "pass1")
                .filter(cookieFilter)
                .when().get(SESSION_NEW_API_URL)
                .then()
                .statusCode(200)
                .body(is("is existing session"));
    }

    @Test
    void twoUsers_multipleRequests_doenstReuseSession() {
        CookieFilter cookieFilter = new CookieFilter();

        given()
                .auth().basic("user1", "pass1")
                .filter(cookieFilter)
                .when().get(SESSION_NEW_API_URL)
                .then()
                .statusCode(200)
                .body(is("is new session"));

        given()
                .auth().basic("user2", "pass2")
                .filter(cookieFilter)
                .when().get(SESSION_NEW_API_URL)
                .then()
                .statusCode(200)
                .body(is("is new session"));
    }
}
