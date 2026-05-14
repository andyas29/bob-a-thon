package org.example.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page object representing a YouTube channel page.
 * <p>
 * This class contains web elements and locators for YouTube channel pages,
 * including subscriber count information.
 * </p>
 *
 * @author Andrei Stoica
 * @version 1.0
 */
public class ChannelPage extends Page {
    /**
     * The web element displaying the subscriber count on a YouTube channel page.
     * Located by the CSS selector {@code yt-content-metadata-view-model > div:nth-child(3) > span}.
     */
    @FindBy(css = "yt-content-metadata-view-model > div:nth-child(3) > span")
    public WebElement subscriberCount;

}
