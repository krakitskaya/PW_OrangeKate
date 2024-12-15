package com.kate.project;

import com.microsoft.playwright.*;
import com.kate.project.helpers.LoginHelper;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.kate.project.helpers.Config;

public class BasePlaywrightTest {
    private static Playwright playwright;
    private static Browser browser;
    protected BrowserContext context;
    protected Page page;
    protected Logger logger;
    protected LoginHelper loginHelper;

    protected final static String BASE_URL = Config.get("BASE_URL");
    protected int width = Config.getInt("viewport.width");
    protected int height = Config.getInt("viewport.height");

    @BeforeAll
    static void setup() {
        Logger logger = LoggerFactory.getLogger(BasePlaywrightTest.class);
        logger.info("Initializing Playwright...");
        playwright = Playwright.create();

        String browserType = Config.getBrowser(); // Read browser type from config
        logger.info("Configuring browser type: {}", browserType);

        switch (browserType) {
            case "firefox":
                browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
                break;
            case "webkit":
                browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false));
                break;
            case "chromium":
            default:
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
                break;
        }

        logger.info("Browser launched successfully.");
    }

    @BeforeEach
    void createContextAndPage() {
        this.logger = LoggerFactory.getLogger(this.getClass());
        logger.info("Creating browser context and page...");
        context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(width, height));
        page = context.newPage();
        try {
            logger.info("Navigating to the base URL...");
            page.navigate(BASE_URL);
            logger.info("Navigation successful.");
        } catch (Exception e) {
            logger.error("Failed to navigate: {}", e.getMessage());
            throw e;
        }

        loginHelper = new LoginHelper(this, page);
    }

    @AfterEach
    void closeContext() {
        if (context != null) {
            logger.info("Closing browser context...");
            context.close();
            logger.info("Browser context closed successfully.");
        }
    }

    @AfterAll
    static void teardown() {
        Logger logger = LoggerFactory.getLogger(BasePlaywrightTest.class);
        if (browser != null) {
            logger.info("Closing browser...");
            browser.close();
            logger.info("Browser closed successfully.");
        }
        if (playwright != null) {
            playwright.close();
            logger.info("Playwright shutdown completed.");
        }
    }
}