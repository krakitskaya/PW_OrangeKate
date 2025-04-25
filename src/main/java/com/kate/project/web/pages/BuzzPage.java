package com.kate.project.web.pages;

import com.kate.project.web.tiles.BuzzPostTile;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class BuzzPage extends BasePage {
    private final Locator postInput;
    private final Locator submitPostButton;
    private final Locator deletePostButton;
    private final Locator confirmDeleteButton;

    public BuzzPage(Page page) {
        super(page);
        postInput = page.locator(".oxd-buzz-post-input");
        submitPostButton = page.locator("[type=\"submit\"]");
        deletePostButton = page.locator(".oxd-icon.bi-trash");
        confirmDeleteButton = page.locator(".oxd-icon.bi-trash.oxd-button-icon");
    }

    public BuzzPage addNewPost(String newPostText) {
        logger.info("Adding new post with text '{}'", newPostText);
        postInput.fill(newPostText);
        page.waitForRequest(r -> r.url().contains("https://opensource-demo.orangehrmlive.com/web/api/v2/buzz/posts"), submitPostButton::click);
        page.reload();
        page.waitForLoadState(LoadState.NETWORKIDLE);
        return this;
    }

    public BuzzPage deletePost(String postText) {
        logger.info("Get a post by text '{}' and delete it", postText);
        page.waitForLoadState(LoadState.NETWORKIDLE);
        getBuzzPostTiles().stream()
                .filter(tile -> tile.getPostText().equals(postText))
                .findFirst()
                .ifPresentOrElse(
                        tile -> {
                            logger.info("Open More Actions for PostTile with text: '{}'", postText);
                            tile.getMoreActionsButton().click();
                            logger.info("Click Delete and Confirm");
                            deletePostButton.click();
                            confirmDeleteButton.click();
                            logger.info("Post with text '{}' deleted.", postText);
                        },
                        () -> {
                            throw new NoSuchElementException("No post found with text: " + postText);
                        }
                );
        page.reload();
        page.waitForLoadState(LoadState.NETWORKIDLE);
        return this;
    }

    public boolean isBuzzPostTilePresent(String postText) {
        page.waitForLoadState(LoadState.NETWORKIDLE);
        List<BuzzPostTile> buzzPostTiles = getBuzzPostTiles();
        return buzzPostTiles.stream()
                .anyMatch(tile -> tile.getPostText().equals(postText));
    }

    public List<BuzzPostTile> getBuzzPostTiles() {
        List<Locator> buzzPostTiles = page.locator("div[class*='orangehrm-buzz-newsfeed-posts'] div[class*='white'][class*='orangehrm-buzz']").all();
        List<BuzzPostTile> buzzPostTilesToReturn = new ArrayList<>();
        for (Locator buzzPostTile : buzzPostTiles) {
            Locator textLocator = buzzPostTile.locator("p.orangehrm-buzz-post-body-text");
            String postText = textLocator.count() > 0 ? textLocator.textContent() : "";
            Locator deleteButton = buzzPostTile.locator("i.oxd-icon.bi-three-dots");
            buzzPostTilesToReturn.add(new BuzzPostTile(postText, deleteButton));
        }
        return buzzPostTilesToReturn;
    }
}
