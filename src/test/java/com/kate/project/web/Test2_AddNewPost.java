package com.kate.project.web;

import com.kate.project.BasePlaywrightTest;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Test2_AddNewPost extends BasePlaywrightTest {

    private static final String postText = UUID.randomUUID().toString();

    @Test
    void addNewPost() {
        boolean isPostPresent = getLoginHelper().loginSuccessfullyAsAdmin()
                .navigateToBuzzPage()
                .addNewPost(postText)
                .isBuzzPostTilePresent(postText);

        assertTrue(isPostPresent, "Post added successfully");
    }
}