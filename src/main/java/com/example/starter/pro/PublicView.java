package com.example.starter.pro;

import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import io.quarkus.arc.Unremovable;

@Route(PublicView.URI)
@Unremovable
@AnonymousAllowed // PermitAll is expected to work in Quarkus but doesn't because it's differently interpreted by Vaadin
public class PublicView extends GreetView {
    public static final String URI = "/public";
}
