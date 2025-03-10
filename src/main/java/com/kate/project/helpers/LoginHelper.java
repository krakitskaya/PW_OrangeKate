package com.kate.project.helpers;

import com.kate.project.web.entities.User;
import com.kate.project.web.pages.DashboardPage;
import com.kate.project.web.pages.LoginPage;
import com.microsoft.playwright.Page;

public class LoginHelper {
    private final Page page;

    public LoginHelper(Page page) {
        this.page = page;
    }

    public DashboardPage loginSuccessfullyAsAdmin() {
        LoginPage loginPage = new LoginPage(page);
        return loginPage.loginSuccess(new User(Config.get("username"), Config.get("password")));
    }
}