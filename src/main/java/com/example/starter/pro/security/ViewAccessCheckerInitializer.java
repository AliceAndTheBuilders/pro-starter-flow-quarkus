package com.example.starter.pro.security;

import com.example.starter.pro.frontend.Paths;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import com.vaadin.flow.server.auth.ViewAccessChecker;

import java.io.Serial;

/**
 * Vaadin required their own mechanism to check permissions on views correctly.
 * This class instantiates and activates vaadins ViewAccessChecker. This class will be instantiated via a vaadin mechanic
 * which searches for the file resources/META-INF/services/com.vaadin.flow.server.VaadinServiceInitListener. The content
 * of the file points to this class.
 */
public class ViewAccessCheckerInitializer implements VaadinServiceInitListener {

	@Serial
	private static final long serialVersionUID = 5279549049112781981L;

	private final ViewAccessChecker viewAccessChecker;

	public ViewAccessCheckerInitializer() {
		viewAccessChecker = new ViewAccessChecker();
		// Set the path to the core login URI for redirects if the user is not authenticated
		viewAccessChecker.setLoginView(Paths.LOGIN);
	}

	@Override
	public void serviceInit(ServiceInitEvent serviceInitEvent) {
		serviceInitEvent.getSource().addUIInitListener(
			uiInitEvent -> uiInitEvent.getUI().addBeforeEnterListener(viewAccessChecker)
		);
	}
}
