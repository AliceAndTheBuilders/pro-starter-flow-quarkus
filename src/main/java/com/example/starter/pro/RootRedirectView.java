package com.example.starter.pro;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import io.quarkus.arc.Unremovable;
import jakarta.annotation.security.PermitAll;

import java.io.Serial;

/**
 * The root uri / leads to problems with finetuning permissions and access to other features like vaadin dev endpoints.
 * This "View" simply sits as free accessible redirect on / and redirects all requests to the actual starting view.
 * The starting view is also the view which will enforce authentication.
 */
@SuppressWarnings("unused")
@Route("")
@Unremovable
@PermitAll
public class RootRedirectView extends VerticalLayout {
	@Serial
	private static final long serialVersionUID = -7000478309055815278L;

	public RootRedirectView() {
		UI.getCurrent().navigateToClient(ProtectedView.URI);
	}
}
