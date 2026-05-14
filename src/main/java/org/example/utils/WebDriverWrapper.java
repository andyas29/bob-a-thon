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

public class WebDriverWrapper {
    private WebDriver driver;

    public WebDriverWrapper(String browser) {
        switch (browser) {
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


    private void setDefaultSettings() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();
    }

    public void quit() {
        driver.quit();
    }

    public WebElementUtil createUtil() {
        return new WebElementUtil(this.driver);
    }
}
