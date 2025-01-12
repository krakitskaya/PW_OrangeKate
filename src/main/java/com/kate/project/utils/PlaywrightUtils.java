package com.kate.project.utils;

import com.microsoft.playwright.*;
import lombok.Getter;

public class PlaywrightUtils {
    private final Playwright playwright;
    @Getter
    private final Browser browser;
    private BrowserContext context;
    private Page page;

    public PlaywrightUtils(String browserType, boolean headless) {
        playwright = Playwright.create();
        browser = switch (browserType.toLowerCase()) {
            case "firefox" -> playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(headless));
            case "webkit" -> playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(headless));
            default -> playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(headless));
        };
    }

    public void closePage() {
        if (page != null) {
            page.close();
            page = null;
        }
        if (context != null) {
            context.close();
            context = null;
        }
    }

    public void close() {
        closePage();
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }
}