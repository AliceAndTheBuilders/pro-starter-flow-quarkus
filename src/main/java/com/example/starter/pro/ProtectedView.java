package com.example.starter.pro;

import com.vaadin.flow.router.Route;
import io.quarkus.arc.Unremovable;
import io.quarkus.security.runtime.SecurityIdentityAssociation;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;

@Route(ProtectedView.URI)
@Unremovable
@RolesAllowed("**") // could use PermitAll, but Quarkus and Vaadin do interpret this differently, thus this improves clarity
public class ProtectedView extends GreetView {

    public static final String URI = "/protected";

    @Inject
    transient SecurityIdentityAssociation identity;

    @PostConstruct // to have injects available
    public void init() {
        textField.setValue(identity.getIdentity().getPrincipal().getName());
    }
}

