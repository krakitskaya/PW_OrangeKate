package com.kate.project.pages;

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

    public HomePage loginSuccess(String username, String password) {
        logger.info("Log in with username: {} and password: {}", username, password);
        usernameInput.fill(username);
        passwordInput.fill(password);
        loginButton.click();
        assertThat(loginButton).isHidden();
        return new HomePage(page);
    }
}
