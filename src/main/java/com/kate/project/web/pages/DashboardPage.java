package com.kate.project.web.pages;

import com.kate.project.web.interfaces.LeftMenu;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class DashboardPage extends BasePage implements LeftMenu {
    public final Locator userMenuLocator;

    public DashboardPage(Page page) {
        super(page);
        userMenuLocator = page.locator(".oxd-topbar-header-userarea");
    }

    public BuzzPage navigateToBuzzPage() {
        logger.info("Navigate to Buzz Page");
        navigateToBuzz();
        return new BuzzPage(page);
    }
}