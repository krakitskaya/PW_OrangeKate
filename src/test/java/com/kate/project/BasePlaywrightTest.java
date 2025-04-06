package com.kate.project;

import com.kate.project.api.Config;
import com.kate.project.web.helpers.LoginHelper;
import com.kate.project.utils.PlaywrightUtils;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasePlaywrightTest {
    private BrowserContext context;
    private Page page;
    private LoginHelper loginHelper;
    protected Logger logger;

    private static final String BASE_URL = Config.get("BASE_URL");
    private static final int WIDTH = Config.getInt("viewport.width");
    private static final int HEIGHT = Config.getInt("viewport.height");

    @BeforeEach
    void setupTest() {
        logger = LoggerFactory.getLogger(this.getClass());

        // Initialize PlaywrightUtils
        PlaywrightUtils playwrightUtils = new PlaywrightUtils(Config.getBrowser(), false); // Adjust headless if needed

        // Create new BrowserContext and Page
        context = playwrightUtils.getBrowser().newContext(new Browser.NewContextOptions().setViewportSize(WIDTH, HEIGHT));
        page = context.newPage();

        // Navigate to base URL
        logger.info("Navigating to {}", BASE_URL);
        page.navigate(BASE_URL);

        // Initialize LoginHelper
        loginHelper = new LoginHelper(page);
    }

    protected Page getPage() {
        return page;
    }

    protected LoginHelper getLoginHelper() {
        return loginHelper;
    }

    @AfterEach
    void teardownTest() {
        // Cleanup Page
        if (page != null) {
            page.close();
        }

        // Cleanup BrowserContext
        if (context != null) {
            context.close();
        }
    }
}
