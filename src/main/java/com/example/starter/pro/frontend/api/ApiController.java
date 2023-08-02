package com.example.starter.pro.frontend.api;

import com.example.starter.pro.frontend.Paths;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.net.URI;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Path(Paths.LOGOUT)
public class ApiController {

    @Inject
    UriInfo uriInfo;

    @ConfigProperty(name = "quarkus.http.auth.form.enabled")
    Optional<Boolean> formAuthEnabled;

    @ConfigProperty(name = "quarkus.http.auth.form.cookie-name")
    Optional<String> formAuthCookieName;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout() {
        log.info("Logout request received.");

        // Redirect to front page, this will trigger a new authentication check and forward the user to the login screen
        URI loginUri = uriInfo.getRequestUriBuilder().replacePath("/").build();
        Response.ResponseBuilder clientResponse = Response.seeOther(loginUri);

        // Only if form auth is enabled we instruct the client to remove the session cookie from cookie jar on logout
        if (Boolean.TRUE.equals(formAuthEnabled.orElse(Boolean.FALSE)) && formAuthCookieName.isPresent()) {
            NewCookie cookie = new NewCookie.Builder(formAuthCookieName.get())
                    .value("")
                    .path("/")
                    .domain(null)
                    .maxAge(0)
                    .expiry(Date.from(Instant.EPOCH))
                    .build();
            clientResponse.cookie(cookie);
        }

        return clientResponse.build();
    }
}
