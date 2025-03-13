package com.kate.project.helpers;

import com.kate.project.web.entities.User;
import com.kate.project.web.pages.DashboardPage;
import com.kate.project.web.pages.LoginPage;
import com.microsoft.playwright.Page;

public class LoginHelper {
    public static final User ADMIN_USER = new User(Config.get("adminUsername"), Config.get("adminPassword"));
    private final Page page;

    public LoginHelper(Page page) {
        this.page = page;
    }

    public DashboardPage loginSuccessfullyAsAdmin() {
        return loginSuccessfully(ADMIN_USER);
    }

    public DashboardPage loginSuccessfully(User user) {
        LoginPage loginPage = new LoginPage(page);
        return loginPage.loginSuccess(user);
    }
}