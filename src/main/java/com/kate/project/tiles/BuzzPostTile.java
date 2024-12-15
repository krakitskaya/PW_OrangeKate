package com.kate.project.tiles;

import com.microsoft.playwright.Locator;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuzzPostTile {
    private final String postText;
    private final Locator moreActionsButton;

    public BuzzPostTile(String postText, Locator moreActionsButton) {
        this.postText = postText;
        this.moreActionsButton = moreActionsButton;
    }
}
