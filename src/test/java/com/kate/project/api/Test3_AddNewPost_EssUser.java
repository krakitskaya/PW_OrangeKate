package com.kate.project.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kate.project.BasePlaywrightTest;
import com.kate.project.helpers.UserHelper;
import com.kate.project.web.entities.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Test3_AddNewPost_EssUser extends BasePlaywrightTest {
    private User essUser;

    private static final String postText = "NewPost";

    @Test
    public void verifyEssUserHasNoAdminTab() throws JsonProcessingException {
        try {
            essUser = UserHelper.createEssUserViaApi();
            getApiClientFactoryFor(essUser).getBuzzApiClient().createNewPost("NewPost");

            boolean isPostPresent = getLoginHelper().loginSuccessfullyAsAdmin()
                    .navigateToBuzzPage()
                    .addNewPost(postText)
                    .isBuzzPostTilePresent(postText);

            assertTrue(isPostPresent, "Post added successfully");
        } finally {
            defaultApiClientFactory.getUserAdminApiClient().deleteUserById(essUser.getUserId());
        }
    }
}