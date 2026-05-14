package org.example.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page object representing the YouTube home page.
 * <p>
 * This class contains web elements and locators for the main YouTube landing page,
 * including the search functionality and cookie consent controls.
 * </p>
 *
 * @author Andrei Stoica
 * @version 1.0
 */
public class HomePage extends Page {
    /**
     * The search input field on the YouTube home page.
     * Located by the CSS selector {@code [name='search_query']}.
     */
    @FindBy(css = "[name='search_query']")
    public WebElement search;
    
    /**
     * The button to reject cookies on the YouTube consent dialog.
     * Located by a complex CSS selector targeting the reject button styling.
     */
    @FindBy(css=".yt-spec-button-shape-next.yt-spec-button-shape-next--filled.yt-spec-button-shape-next--mono.yt-spec-button-shape-next--size-m")
    public WebElement rejectCookiesBtn;
}
