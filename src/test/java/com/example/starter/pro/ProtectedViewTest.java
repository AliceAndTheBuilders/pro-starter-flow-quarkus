package com.example.starter.pro;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.textfield.TextField;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
class ProtectedViewTest extends QuarkusIsolatedUIBaseTest {

    // Didn't find a pretty way yet to check denied access in UI Unit tests
    // IndexOutOfBoundsException is thrown in such case anyway
    @Test
    void render_noAuth_accessDenied() {
        // GIVEN - no auth

        // WHEN - navigating to the protected view IndexOutOfBoundsException is thrown if protected
        try {
            uiTestHelper.navigate(ProtectedView.class);
        } catch (IndexOutOfBoundsException e) {
            Assertions.assertTrue(true);
            return;
        }

        // If not catched fail since view was accessible
        Assertions.fail();
    }

    @Test
    @TestSecurity(user = "vaadin", roles = "vaadin")
    void render_noAction_expectUsernameOnTextField() {
        // GIVEN
        uiTestHelper.navigate(ProtectedView.class);

        // WHEN
        String textFieldValue = uiTestHelper.$(TextField.class).first().getValue();

        // THEN
        Assertions.assertEquals("vaadin", textFieldValue);
    }

    @Test
    @TestSecurity(user = "vaadin", roles = "vaadin")
    void render_fillName_expectParagraphWithName() {
        // GIVEN
        uiTestHelper.navigate(ProtectedView.class);

        // WHEN
        test(uiTestHelper.$(TextField.class).first()).setValue("quarkus");
        test(uiTestHelper.$(Button.class).first()).click();

        // THEN
        Paragraph p = uiTestHelper.$(Paragraph.class).first();

        Assertions.assertNotNull(p);
        Assertions.assertEquals("Hello quarkus", uiTestHelper.$(Paragraph.class).first().getText());
    }
}
