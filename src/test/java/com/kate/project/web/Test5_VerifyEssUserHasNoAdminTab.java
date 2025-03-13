package com.kate.project.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kate.project.helpers.UserHelper;
import com.kate.project.web.entities.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class Test5_VerifyEssUserHasNoAdminTab extends BasePlaywrightTest {

    @Test
    public void verifyEssUserHasNoAdminTab() throws JsonProcessingException {
        User essUser = UserHelper.createEssUserViaApi();
        assertFalse(getLoginHelper().loginSuccessfully(essUser)
                .isAdminTabVisible());
        UserHelper.deleteUserViaApi(essUser);
    }
}