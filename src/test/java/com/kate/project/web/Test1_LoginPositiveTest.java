package com.kate.project.web;

import com.kate.project.BasePlaywrightTest;
import com.kate.project.web.pages.DashboardPage;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class Test1_LoginPositiveTest extends BasePlaywrightTest {

    @Test
    void loginPositive() {
        getLoginHelper().loginSuccessfullyAsAdmin();
        assertThat(new DashboardPage(getPage()).userMenuLocator).isVisible();
    }
}