package com.example.starter.pro.frontend.api;

import com.example.starter.pro.frontend.Paths;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Date;

import static io.restassured.RestAssured.given;

@QuarkusTest
@Slf4j
class ApiControllerTest {

    private static final String QUARKUS_COOKIE = "quarkus-credential";

    @Test
    void logout_validCredentials_cookieDeleted() {
        // GIVEN - logged in
        String body = "username=vaadin&password=vaadin";
        String session = given()
                .body(body)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .post(Paths.AUTH)
                .then()
                .statusCode(HttpStatus.SC_MOVED_TEMPORARILY)
                .extract()
                .cookie(QUARKUS_COOKIE);

        // WHEN
        Cookies cookies = given()
                .redirects().follow(false)
                .cookie(QUARKUS_COOKIE, session)
                .get(Paths.LOGOUT)
                .then()
                .extract().detailedCookies();

        // THEN
        Cookie resetCookie = cookies.get(QUARKUS_COOKIE);
        Assertions.assertEquals(Date.from(Instant.EPOCH), resetCookie.getExpiryDate());
        Assertions.assertEquals(0, resetCookie.getMaxAge());
    }
}
