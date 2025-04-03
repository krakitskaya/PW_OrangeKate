package com.kate.project.web;

import com.kate.project.BasePlaywrightTest;
import com.kate.project.helpers.BuzzHelper;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class Test3_DeleteNewPost extends BasePlaywrightTest {
    private static final String postText = UUID.randomUUID().toString();

    @Test
    void deleteNewPost() {
        BuzzHelper.createPostApi(postText);

        boolean isPostPresent = getLoginHelper().loginSuccessfullyAsAdmin()
                .navigateToBuzzPage()
                .deletePost(postText)
                .isBuzzPostTilePresent(postText);

        assertFalse(isPostPresent, "Post deleted successfully");
    }
}