package org.example.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;

public class WebElementUtil {
    private final WebDriver driver;

    public WebElementUtil(WebDriver driver) {
        this.driver = driver;
    }

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

    public <T> T pageFactory(Class<T> page) {
        return PageFactory.initElements(driver, page);
    }

    public void get(String url) {
        driver.get(url);
    }

    public void sendKeys(WebElement element, String charSequence) {
        element.sendKeys(charSequence + Keys.ENTER);
    }
}
