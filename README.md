# Project Starter for Vaadin Flow and Quarkus with UI Unit Testing

This is a fork of https://github.com/vaadin/base-starter-flow-quarkus.

This project can be used as a starting point to create your own Vaadin Flow application for Quarkus. It contains all the necessary configuration with some placeholder files to get you started.

Focus on this project (compared to the base starter at https://github.com/vaadin/base-starter-flow-quarkus) is to illustrate usage with UI Unit testing Pro feature and Quarkus authentication / protected routes.

Attention: There are currently some limitations i vaadins quarkus implementation so this project shows how to workaround these issues. Anyway official support for UI Unit Tests in Vaadin for Quarkus would be preferred.

Related issue: https://github.com/vaadin/testbench/issues/1655

Quarkus 3.1+ requires Java 17.

## Contained workarounds & outlined stumbling blocks

- auto discovering and registering vaadin routes
- UI Unit Tests for quarkus with authentication support
- Using UIUnitTest while having another base test class one may want to extend (which is not UI related)
- register and initialize ViewAccessChecker
- Login form for Quarkus
- @PermitAll in Vaadin is a synonym for @RolesAllowed("**") which does not match Quarkus definition
- Correct order of BOM imports (https://github.com/vaadin/quarkus/issues/124)