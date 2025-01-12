package com.kate.project.web.tiles;

import com.microsoft.playwright.Locator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BuzzPostTile {
    private final String postText;
    private final Locator moreActionsButton;
}
