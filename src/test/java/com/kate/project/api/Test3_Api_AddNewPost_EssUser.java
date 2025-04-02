package com.kate.project.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kate.project.BasePlaywrightTest;
import com.kate.project.helpers.UserHelper;
import com.kate.project.web.entities.User;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Test3_Api_AddNewPost_EssUser extends BasePlaywrightTest {
    private User essUser;
    private static final String postText = UUID.randomUUID().toString();

    @Test
    public void addNewPostByEssUser() throws JsonProcessingException {
        try {
            essUser = UserHelper.createEssUserViaApi();
            getApiClientFactoryFor(essUser).getBuzzApiClient().createNewPost(postText);

            boolean isPostPresent = getLoginHelper().loginSuccessfullyAsAdmin()
                    .navigateToBuzzPage()
                    .isBuzzPostTilePresent(postText);

            assertTrue(isPostPresent, "Post added successfully");
        } finally {
            defaultApiClientFactory.getUserAdminApiClient().deleteUserById(essUser.getUserId());
        }
    }
}