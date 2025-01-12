package com.kate.project.api;

import com.kate.project.helpers.Config;
import com.kate.project.utils.PlaywrightUtils;
import com.kate.project.helpers.LoginHelper;
import io.restassured.RestAssured;
import lombok.Getter;

public class ApiTestConfig {
    @Getter
    private static String sessionCookie;
    private static final String BASE_URI = Config.get("BASE_URL") + "/api/v2";

    public static void initializePlaywrightAndLogin() {
        // Set base URI for RestAssured
        RestAssured.baseURI = BASE_URI;

        // Initialize Playwright and login
        PlaywrightUtils playwrightUtils = new PlaywrightUtils(Config.get("browser"), true); // Adjust headless as needed
        var page = playwrightUtils.getBrowser()
                .newContext()
                .newPage();

        // Navigate to the login page
        String loginPageUrl = Config.get("BASE_URL") + "/auth/login";
        page.navigate(loginPageUrl);

        // Perform login
        LoginHelper loginHelper = new LoginHelper(page);
        loginHelper.loginSuccessfullyAsAdmin();

        // Extract session cookie
        sessionCookie = page.context().cookies().stream()
                .filter(cookie -> "orangehrm".equals(cookie.name))
                .findFirst()
                .map(cookie -> cookie.value)
                .orElseThrow(() -> new RuntimeException("Session cookie not found"));

        // Cleanup after login
        page.close();
        playwrightUtils.close();
    }

    public static String getBaseUri() {
        return BASE_URI;
    }
}