package com.example.starter.pro.frontend.view;

import com.example.starter.pro.frontend.Paths;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.Route;
import io.quarkus.arc.Unremovable;
import io.quarkus.security.runtime.SecurityIdentityAssociation;
import jakarta.annotation.security.RolesAllowed;

@Route(Paths.PROTECTED)
@Unremovable
@RolesAllowed("**") // could use PermitAll, but Quarkus and Vaadin do interpret this differently, thus this improves clarity
public class ProtectedView extends GreetView {

    transient SecurityIdentityAssociation identity;

    public ProtectedView(SecurityIdentityAssociation identity) {
        this.identity = identity;

        initView();
    }

    public void initView() {
        textField.setValue(identity.getIdentity().getPrincipal().getName());

        Button logout = new Button(new Icon(VaadinIcon.SIGN_OUT),
                e -> UI.getCurrent().getPage().setLocation(Paths.LOGOUT));

        add(logout);
    }
}

