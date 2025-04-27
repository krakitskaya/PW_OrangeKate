package com.kate.project.common.helpers;

import com.kate.project.api.dto.BuzzPostDto;
import com.kate.project.api.factory.ApiClientFactory;
import com.kate.project.web.entities.User;

import static com.kate.project.common.Users.ADMIN_USER;

public class BuzzHelper {

    public static int createPostApi(User user, String text) {
        var response = new ApiClientFactory()
                .getBuzzApiClient()
                .createPost(new BuzzPostDto(text), user);

        response.assertSuccess();
        return response.body();
    }

    public static int createPostApi(String text) {
        return createPostApi(ADMIN_USER, text);
    }
}