package com.kate.project.pages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.microsoft.playwright.Page;

public class BasePage {
    protected Page page;
    protected final Logger logger;

    public BasePage(Page page) {
        this.page = page;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }
}
