package org.example.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page object representing a YouTube video page.
 * <p>
 * This class contains web elements and locators for YouTube video pages,
 * including the video title, channel information, and bottom row controls.
 * </p>
 *
 * @author Andrei Stoica
 * @version 1.0
 */
public class VideoPage extends Page {
    /**
     * The web element displaying the video title.
     * Located by the CSS selector {@code #title}.
     */
    @FindBy(css = "#title")
    public WebElement title;
    
    /**
     * The web element representing the bottom row of the video page,
     * which typically contains video controls and interaction buttons.
     * Located by the CSS selector {@code #bottom-row}.
     */
    @FindBy(css = "#bottom-row")
    public WebElement bottomRow;
    
    /**
     * The web element displaying the channel name of the video uploader.
     * Located by the CSS selector {@code #owner #channel-name}.
     */
    @FindBy(css = "#owner #channel-name")
    public WebElement channelName;
}
