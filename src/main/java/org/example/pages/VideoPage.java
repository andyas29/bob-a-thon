package org.example.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class VideoPage extends Page {
    @FindBy(css = "#title")
    public WebElement title;
    @FindBy(css = "#bottom-row")
    public WebElement bottomRow;
    @FindBy(css = "#owner #channel-name")
    public WebElement channelName;
}
