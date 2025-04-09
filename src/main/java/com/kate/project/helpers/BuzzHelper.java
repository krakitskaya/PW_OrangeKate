package com.kate.project.helpers;

import com.kate.project.api.dto.BuzzPostDto;
import com.kate.project.api.interfaces.ResponseVerifier;
import com.kate.project.factory.ApiClientFactory;
import com.kate.project.web.entities.User;

public class BuzzHelper implements ResponseVerifier {

    public static int createPostApi(User user, String text) {
        return new ApiClientFactory(user)
                .getBuzzApiClient()
                .createPostAndVerifySuccess(new BuzzPostDto(text));
    }

    public static int createPostApi(String text) {
        return new ApiClientFactory()
                .getBuzzApiClient()
                .createPostAndVerifySuccess(new BuzzPostDto(text));
    }
}
