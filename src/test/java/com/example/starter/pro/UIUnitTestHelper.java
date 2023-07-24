package com.example.starter.pro;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.di.DefaultInstantiator;
import com.vaadin.flow.di.Instantiator;
import com.vaadin.flow.di.InstantiatorFactory;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.quarkus.QuarkusInstantiator;
import com.vaadin.testbench.unit.UIUnitTest;
import com.vaadin.testbench.unit.internal.MockVaadin;
import com.vaadin.testbench.unit.internal.Routes;
import com.vaadin.testbench.unit.mocks.MockHttpSession;
import com.vaadin.testbench.unit.mocks.MockRequest;
import com.vaadin.testbench.unit.mocks.MockedUI;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.runtime.QuarkusPrincipal;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.spi.BeanManager;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.inject.Inject;
import lombok.Getter;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
public class UIUnitTestHelper extends UIUnitTest {

    private static final String ROLE_MATCH_ANY = "**";

    @Inject
    SecurityIdentity securityIdentity;

    public void init() {
        // Add all routes
        Routes routes = new Routes().merge(
                new Routes(autoDiscoverViews(), Set.of(), true)
        );

        // Set custom MockRequestFactory. This allows us to extend the mocked request with our principal and userInRole method
        MockVaadin.INSTANCE.setMockRequestFactory((MockHttpSession session) -> {
            MockRequest mockRequest = new MockRequest(session);

            // Do not set principal if provided from AnonymousSecurityIdentityProvider like HttpServlet does for anonymous requests
            // Otherwise com.vaadin.flow.server.auth.AccessAnnotationChecker.hasAccess would allow access
            // for RolesAllowed(**) because principal is not null
            if (!securityIdentity.isAnonymous()) {
                mockRequest.setUserPrincipalInt(new PrincipalWithRoles(securityIdentity));
                mockRequest.setUserInRole(UIUnitTestHelper::isUserInRole);
            }

            return mockRequest;
        });

        // Actual setup with the quarkus based bean instantiation
        MockVaadin.setup(routes, MockedUI::new, Set.of(TestInstantiatorFactory.class));
    }

    public void clear() {
        super.cleanVaadinEnvironment();
    }

    protected Set<Class<? extends Component>> autoDiscoverViews() {
        Set<Class<? extends Component>> routes = new HashSet<>();

        // Use the quarkus runtime class loader
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        ClassGraph classGraph = new ClassGraph()
                .enableClassInfo()
                .enableAnnotationInfo()
                .acceptPackages("");

        try (ScanResult scanResult = classGraph.scan()) {
            for (ClassInfo info : scanResult.getClassesWithAnnotation(Route.class.getName())) {
                routes.add((Class<? extends Component>) classLoader.loadClass(info.getName()));
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return routes;
    }

    /**
     * QuarkusPrincipal is not aware of roles, this is done by the SecurityIdentity. Vaadins UserInRole Method however
     * only accepts the Principal and the required role as an argument. To deal with this we create a new principal-variant
     * which also contains the user roles.
     */
    private static class PrincipalWithRoles extends QuarkusPrincipal {
        @Getter
        Set<String> roles;

        public PrincipalWithRoles(SecurityIdentity identity) {
            super(identity.getPrincipal().getName());
            this.roles = identity.getRoles();
        }
    }

    /**
     * Very simple check to test if a user has a certain role
     */
    private static boolean isUserInRole(Principal principal, String role) {
        if (!(principal instanceof PrincipalWithRoles)) {
            return false;
        }

        if (ROLE_MATCH_ANY.equals(role)) {
            return true;
        }

        return ((PrincipalWithRoles)principal).getRoles().contains(role);
    }

    public static class TestInstantiatorFactory implements InstantiatorFactory {
        @Override
        public Instantiator createInstantitor(VaadinService service) {
            BeanManager beanManager = CDI.current().getBeanManager();
            return new QuarkusInstantiator(new DefaultInstantiator(service), beanManager);
        }
    }
}
