package com.kate.project.web.interfaces;

import com.microsoft.playwright.Page;

public interface LeftMenu {

    Page getPage();

    default void navigateToBuzz() {
        navigateTo("Buzz");
    }

    private void navigateTo(String menuItem) {
        getPage().locator(".oxd-navbar-nav").locator(String.format("[class*='oxd-main-menu-item--name']:has-text('%s')", menuItem))
                .click();
    }
}