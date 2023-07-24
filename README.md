# Project Starter for Vaadin Flow and Quarkus with UI Unit Testing

This project can be used as a starting point to create your own Vaadin Flow application for Quarkus. It contains all the necessary configuration with some placeholder files to get you started.

Focus on this project (compared to the base starter at https://github.com/vaadin/base-starter-flow-quarkus) is to illustrate usage with UI Unit testing Pro feature and Quarkus authentication / protected routes.

Attention: There are currently some limitations i vaadins quarkus implementation so this project shows how to workaround these issues. Anyway official support for UI Unit Tests in Vaadin for Quarkus would be preferred.

Quarkus 3.1+ requires Java 17.

## Contained workarounds & outlined stumbling blocks

- auto discovering and registering vaadin routes
- UI Unit Tests for quarkus with authentication support
- register and initialize ViewAccessChecker
- Login form for Quarkus
- @PermitAll in Vaadin is a synomym for @RolesAllowed("**") which does not match Quarkus definition
