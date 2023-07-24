package com.example.starter.pro;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.textfield.TextField;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
class PublicViewTest extends QuarkusIsolatedUIBaseTest {

    @Test
    void render_fillName_expectParagraphWithName() {
        // GIVEN
        uiTestHelper.navigate(LoginView.class);

        // WHEN
        test(uiTestHelper.$(TextField.class).first()).setValue("quarkus");
        test(uiTestHelper.$(Button.class).first()).click();

        // THEN
        Paragraph p = uiTestHelper.$(Paragraph.class).first();

        Assertions.assertNotNull(p);
        Assertions.assertEquals("Hello quarkus", uiTestHelper.$(Paragraph.class).first().getText());
    }
}
