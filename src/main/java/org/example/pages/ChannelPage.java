package org.example.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ChannelPage extends Page {
    @FindBy(css = "yt-content-metadata-view-model > div:nth-child(3) > span")
    public WebElement subscriberCount;

}
