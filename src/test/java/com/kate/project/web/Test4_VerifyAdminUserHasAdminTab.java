package com.kate.project.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kate.project.helpers.UserHelper;
import com.kate.project.web.entities.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Test4_VerifyAdminUserHasAdminTab extends BasePlaywrightTest {

    @Test
    public void verifyAdminUserHasAdminTab() throws JsonProcessingException {
        User adminUser = UserHelper.createAdminUserViaApi();

        assertTrue(getLoginHelper().loginSuccessfully(adminUser)
                .isAdminTabVisible());
        UserHelper.deleteUserViaApi(adminUser);
    }
}