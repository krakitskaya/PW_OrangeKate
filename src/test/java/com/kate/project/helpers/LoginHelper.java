package com.kate.project.helpers;

import com.kate.project.BasePlaywrightTest;
import com.kate.project.pages.BuzzPage;
import com.kate.project.pages.HomePage;
import com.kate.project.pages.LoginPage;
import com.microsoft.playwright.Page;

public class LoginHelper {

    private final Page page;

    public LoginHelper(BasePlaywrightTest testInstance, Page page) {
        this.page = page;
    }

    public HomePage loginSuccessfully() {
        LoginPage loginPage = new LoginPage(page);
        return loginPage.loginSuccess(Config.get("username"), Config.get("password"));
    }

    public BuzzPage loginToBuzz() {
        return loginSuccessfully()
                .navigateToBuzzPage();
    }
}
