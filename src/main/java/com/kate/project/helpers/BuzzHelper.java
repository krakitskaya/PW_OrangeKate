package com.kate.project.helpers;

import com.kate.project.api.interfaces.ResponseVerifier;
import com.kate.project.factory.ApiClientFactory;
import com.kate.project.web.entities.User;

public class BuzzHelper implements ResponseVerifier {

    public static int createPostApi(ApiClientFactory factory, String text) {
        return factory
                .getBuzzApiClient()
                .getCreatePostClient(text)
                .sendAndExtractPostId();
    }

    public static int createPostApi(User user, String text) {
        return createPostApi(new ApiClientFactory(user), text);
    }

    public static int createPostApi(String text) {
        return createPostApi(new ApiClientFactory(), text);
    }
}
