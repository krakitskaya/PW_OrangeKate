package com.kate.project.web;

import com.kate.project.BasePlaywrightTest;
import com.kate.project.common.helpers.UserHelper;
import com.kate.project.web.entities.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Test4_VerifyAdminUserHasAdminTab extends BasePlaywrightTest {

    @Test
    public void verifyAdminUserHasAdminTab() {
        User adminUser = UserHelper.createAdminUserViaApi();

        assertTrue(getLoginHelper().loginSuccessfully(adminUser)
                .isAdminTabVisible());
        UserHelper.deleteUserViaApi(adminUser);
    }
}