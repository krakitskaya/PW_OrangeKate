package com.kate.project.web.interfaces;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.WaitForSelectorState;

public interface LeftMenu {

    Page getPage();

    default void navigateToBuzz() {
        navigateTo("Buzz");
    }

    default boolean isAdminTabVisible(){
        return isMenuItemVisible("Admin");
    }

    private void navigateTo(String menuItem) {
        getPage().locator(".oxd-navbar-nav").locator(String.format("[class*='oxd-main-menu-item--name']:has-text('%s')", menuItem))
                .click();
    }

    private boolean isMenuItemVisible(String menuItem){
        try {
            getPage().waitForSelector(
                    String.format(".oxd-navbar-nav [class*='oxd-main-menu-item--name']:has-text('%s')", menuItem),
                    new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(3000)
            );
            return true;
        } catch (PlaywrightException e) {
            return false;
        }
    }
}