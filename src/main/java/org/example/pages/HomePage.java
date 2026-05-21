package org.example.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page object representing the YouTube home page.
 * <p>
 * This class contains web elements and locators for the main YouTube landing page,
 * including the search functionality.
 * </p>
 *
 * @author Andrei Stoica
 * @version 1.0
 */
public class HomePage extends Page {
    /**
     * Default constructor for the HomePage class.
     */
    public HomePage() {
    }

    /**
     * The search input field on the YouTube home page.
     * Located by the CSS selector {@code [name='search_query']}.
     */
    @FindBy(css = "[name='search_query']")
    public WebElement search;
}
