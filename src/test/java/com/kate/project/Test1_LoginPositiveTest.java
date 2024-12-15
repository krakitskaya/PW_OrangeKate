package com.kate.project;

import org.junit.jupiter.api.Test;

public class Test1_LoginPositiveTest extends BasePlaywrightTest {

    @Test
    void testLogin() {
        //TODO check how to add page with Insert annotation of JUnit
        loginHelper.loginSuccessfully()
                .verifyHomePagePresent();
    }
}
