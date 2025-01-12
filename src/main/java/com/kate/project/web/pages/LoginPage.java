package com.kate.project.web.pages;

import com.kate.project.web.entities.User;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginPage extends BasePage {
    private final Locator usernameInput;
    private final Locator passwordInput;
    private final Locator loginButton;

    public LoginPage(Page page) {
        super(page);
        usernameInput = page.locator("[name=username]");
        passwordInput = page.locator("[name=password]");
        loginButton = page.locator("button[type=submit]");
    }

    public DashboardPage loginSuccess(User user) {
        logger.info("Log in with username: {} and password: {}", user.getUsername(), user.getPassword());
        usernameInput.fill(user.getUsername());
        passwordInput.fill(user.getPassword());
        loginButton.click();
        assertThat(loginButton).isHidden();
        return new DashboardPage(page);
    }
}
