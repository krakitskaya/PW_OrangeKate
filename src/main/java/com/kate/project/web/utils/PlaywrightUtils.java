package com.kate.project.web.utils;

import com.microsoft.playwright.*;
import lombok.Getter;

@Getter
public class PlaywrightUtils {
    private final Browser browser;

    public PlaywrightUtils(String browserType, boolean headless) {
        Playwright playwright = Playwright.create();
        browser = switch (browserType.toLowerCase()) {
            case "firefox" -> playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(headless));
            case "webkit" -> playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(headless));
            default -> playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(headless));
        };
    }
}