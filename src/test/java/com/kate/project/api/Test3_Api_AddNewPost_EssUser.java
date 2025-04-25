package com.kate.project.api;

import com.kate.project.common.helpers.BuzzHelper;
import com.kate.project.common.helpers.UserHelper;
import com.kate.project.web.entities.User;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Test3_Api_AddNewPost_EssUser extends BaseApiTest {
    private User essUser;
    private static final String postText = UUID.randomUUID().toString();

    @Test
    public void addNewPostByEssUser() {
        try {
            essUser = UserHelper.createEssUserViaApi();
            BuzzHelper.createPostApi(essUser, postText);

            boolean isPostPresent = getLoginHelper().loginSuccessfullyAsAdmin()
                    .navigateToBuzzPage()
                    .isBuzzPostTilePresent(postText);

            assertTrue(isPostPresent, "Post added successfully");
        } finally {
            UserHelper.deleteUserViaApi(essUser);
        }
    }
}