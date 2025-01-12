package com.kate.project.web.pages;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.microsoft.playwright.Page;

@Getter
public class BasePage {
    protected Page page;
    protected final Logger logger;

    public BasePage(Page page) {
        this.page = page;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }
}
