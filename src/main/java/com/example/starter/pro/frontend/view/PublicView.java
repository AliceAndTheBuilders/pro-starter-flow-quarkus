package com.example.starter.pro.frontend.view;

import com.example.starter.pro.frontend.Paths;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import io.quarkus.arc.Unremovable;

@Route(Paths.PUBLIC)
@Unremovable
@AnonymousAllowed // PermitAll is expected to work in Quarkus but doesn't because it's differently interpreted by Vaadin
public class PublicView extends GreetView {
}
