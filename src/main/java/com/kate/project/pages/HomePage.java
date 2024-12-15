package com.kate.project.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class HomePage extends BasePage {
    private final Locator userMenuLocator;
    private final Locator buzzMenu;

    public HomePage(Page page) {
        super(page);
        userMenuLocator = page.locator(".oxd-topbar-header-userarea");
        buzzMenu = page.locator("a.oxd-main-menu-item[href='/web/buzz/viewBuzz']");
    }

    public void verifyHomePagePresent() {
        logger.info("Verify HomePage is Visible...");
        assertThat(userMenuLocator).isVisible();
        logger.info("HomePage is Visible");
    }

    public BuzzPage navigateToBuzzPage() {
        logger.info("Navigate to Buzz Page");
        buzzMenu.click();
        return new BuzzPage(page);
    }
}
