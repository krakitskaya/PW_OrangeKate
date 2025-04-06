package com.kate.project.api.clients;

import com.kate.project.api.clients.buzz.CreatePostClient;
import com.kate.project.api.clients.buzz.DeletePostClient;
import com.kate.project.api.clients.buzz.GetPostsClient;
import com.kate.project.web.entities.User;

public class BuzzApiClient extends BaseApiClient {
    public BuzzApiClient(User user) {
        super(user);
    }

    public CreatePostClient getCreatePostClient(String text) {
        return new CreatePostClient(context, text);
    }

    public DeletePostClient getDeletePostClient(int postId) {
        return new DeletePostClient(context, postId);
    }

    public GetPostsClient getPostsClient() {
        return new GetPostsClient(context);
    }
}