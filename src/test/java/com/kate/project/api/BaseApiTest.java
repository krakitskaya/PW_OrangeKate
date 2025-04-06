package com.kate.project.api;

import com.kate.project.BasePlaywrightTest;
import com.kate.project.api.interfaces.ResponseVerifier;
import com.kate.project.factory.ApiClientFactory;

public abstract class BaseApiTest extends BasePlaywrightTest implements ResponseVerifier {
    protected static final ApiClientFactory defaultApiClientFactory = new ApiClientFactory();
}
