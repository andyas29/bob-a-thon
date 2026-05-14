package org.example.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;

/**
 * Utility class providing convenient methods for interacting with web elements
 * using Selenium WebDriver.
 * <p>
 * This class wraps common WebDriver operations and provides enhanced functionality
 * such as automatic scrolling before clicks and page object initialization.
 * </p>
 *
 * @author Andrei Stoica
 * @version 1.0
 */
public class WebElementUtil {
    private final WebDriver driver;

    /**
     * Constructs a new WebElementUtil with the specified WebDriver instance.
     *
     * @param driver the WebDriver instance to use for element interactions
     */
    public WebElementUtil(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Clicks on the specified web element with automatic scrolling.
     * <p>
     * This method scrolls the element into view before attempting to click it,
     * which helps prevent ElementNotInteractableException errors. If the click
     * fails due to another element intercepting it, a descriptive exception is thrown.
     * </p>
     *
     * @param element the web element to click
     * @throws ElementNotInteractableException if the element is in the DOM but another
     *                                         element receives the click
     * @throws RuntimeException if any other error occurs during the click operation
     */
    public void click(WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].scrollIntoView(true);", element);
        try {
            element.click();
        } catch (ElementNotInteractableException e) {
            throw new ElementNotInteractableException(element.toString()+ " is in DOM but another element gets the click");
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        System.out.println("clicked on " + element.toString());
    }

    /**
     * Initializes and returns a page object instance using Selenium's PageFactory.
     * <p>
     * This method creates a new instance of the specified page class and initializes
     * all {@code @FindBy} annotated fields within it.
     * </p>
     *
     * @param <T> the type of the page object to create
     * @param page the Class object representing the page object type
     * @return a new instance of the page object with initialized web elements
     */
    public <T> T pageFactory(Class<T> page) {
        return PageFactory.initElements(driver, page);
    }

    /**
     * Navigates the browser to the specified URL.
     *
     * @param url the URL to navigate to
     */
    public void get(String url) {
        driver.get(url);
    }

    /**
     * Sends the specified character sequence to the web element followed by the ENTER key.
     * <p>
     * This method is useful for form submissions where pressing ENTER after typing
     * is required (e.g., search boxes).
     * </p>
     *
     * @param element the web element to send keys to
     * @param charSequence the character sequence to type into the element
     */
    public void sendKeys(WebElement element, String charSequence) {
        element.sendKeys(charSequence + Keys.ENTER);
    }
}
