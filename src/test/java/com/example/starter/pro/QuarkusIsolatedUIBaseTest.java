package com.example.starter.pro;

import com.vaadin.testbench.unit.TesterWrappers;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

/**
 * Base class for UI tests which require a clean application state before each test method run
 */
@Slf4j
@SuppressWarnings("java:S2187") // This is the parent class for all derived subtests and thus has no own tests
public class QuarkusIsolatedUIBaseTest implements TesterWrappers {

    @Inject
    protected UIUnitTestHelper uiTestHelper;

    @BeforeEach
    protected void resetBeforeEach() {
        uiTestHelper.init();
    }

    @AfterEach
    protected void resetAfterEach() {
        uiTestHelper.clear();
    }
}
