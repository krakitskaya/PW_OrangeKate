package com.kate.project;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.TestInstance;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Test2_AddAndDeleteNewPost extends BasePlaywrightTest {

    private static final String postAdded = UUID.randomUUID().toString();
    private static boolean isPostAddedSuccessfully;

    @Test
    void addNewPost() {
        boolean isPostPresent = loginHelper.loginToBuzz()
                .addNewPost(postAdded)
                .isBuzzPostTilePresent(postAdded);

        assertTrue(isPostPresent, "Post added successfully");
        isPostAddedSuccessfully = isPostPresent;
    }

    @Test
    void deleteNewPost() {
        Assumptions.assumeTrue(isPostAddedSuccessfully, "Skipping delete test because addNewPost failed");

        boolean isPostPresent = loginHelper.loginToBuzz()
                .deletePost(postAdded)
                .isBuzzPostTilePresent(postAdded);

        assertFalse(isPostPresent, "Post deleted successfully");
    }
}