package org.example.utils;

import org.example.constants.Browsers;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.time.Duration;

/**
 * Wrapper class for Selenium WebDriver that provides simplified browser initialization
 * and configuration management.
 * <p>
 * This class supports multiple browser types (Chrome, Firefox, Edge, Remote) and applies
 * default settings such as implicit waits and window maximization.
 * </p>
 *
 * @author Andrei Stoica
 * @version 1.0
 */
public class WebDriverWrapper {
    private WebDriver driver;

    /**
     * Constructs a new WebDriverWrapper with the specified browser type.
     * <p>
     * Initializes the appropriate WebDriver instance based on the browser parameter
     * and applies default settings.
     * </p>
     *
     * @param browser the browser type to initialize. Valid values are defined in
     *                {@link Browsers} constants (chrome, firefox, edge, remote)
     * @throws IllegalArgumentException if an unsupported browser type is provided
     */
    public WebDriverWrapper(String browser) {
        switch (browser) {
            default -> throw new IllegalArgumentException("Invalid browser type");
            case Browsers.CHROME -> driver = new ChromeDriver();
            case Browsers.EDGE -> driver = new EdgeDriver();
            case Browsers.FIREFOX -> driver = new FirefoxDriver();
            case "remote"->{
                Capabilities capabilities = new ChromeOptions();
                driver= new RemoteWebDriver(capabilities);
            }
            
        }
        setDefaultSettings();
    }


    /**
     * Applies default settings to the WebDriver instance.
     * <p>
     * Default settings include:
     * <ul>
     *   <li>Implicit wait of 5 seconds</li>
     *   <li>Maximized browser window</li>
     * </ul>
     * </p>
     */
    private void setDefaultSettings() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();
    }

    /**
     * Quits the WebDriver instance and closes all associated browser windows.
     * <p>
     * This method should be called in test cleanup methods to ensure proper
     * resource management and prevent memory leaks.
     * </p>
     */
    public void quit() {
        driver.quit();
    }

    /**
     * Creates and returns a new WebElementUtil instance for this WebDriver.
     * <p>
     * The WebElementUtil provides convenient methods for interacting with web elements.
     * </p>
     *
     * @return a new {@link WebElementUtil} instance associated with this WebDriver
     */
    public WebElementUtil createUtil() {
        return new WebElementUtil(this.driver);
    }
}
