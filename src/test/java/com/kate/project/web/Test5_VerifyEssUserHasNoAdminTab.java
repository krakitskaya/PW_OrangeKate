package com.kate.project.web;

import com.kate.project.BasePlaywrightTest;
import com.kate.project.common.helpers.UserHelper;
import com.kate.project.web.entities.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class Test5_VerifyEssUserHasNoAdminTab extends BasePlaywrightTest {

    @Test
    public void verifyEssUserHasNoAdminTab() {
        User essUser = UserHelper.createEssUserViaApi();
        assertFalse(getLoginHelper().loginSuccessfully(essUser)
                .isAdminTabVisible());
        UserHelper.deleteUserViaApi(essUser);
    }
}