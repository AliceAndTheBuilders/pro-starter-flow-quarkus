package com.example.starter.pro.frontend.view;

import com.example.starter.pro.frontend.Paths;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import io.quarkus.arc.Unremovable;

@Route(Paths.LOGIN)
@Unremovable
@AnonymousAllowed // PermitAll is expected to work in Quarkus but doesn't because it's differently interpreted by Vaadin
public class LoginView extends VerticalLayout {

    public LoginView() {
        LoginForm form = new LoginForm();
        form.setAction(Paths.AUTH);

        // Use custom CSS classes to apply styling. This is defined in shared-styles.css.
        addClassName("centered-content");

        add(form);
    }
}
