package steps;

import org.example.pages.ChannelPage;
import org.example.pages.VideoPage;
import org.example.requests.Request;
import org.example.utils.ConfigLoader;
import org.example.utils.WebDriverWrapper;
import org.example.pages.HomePage;
import org.example.utils.WebElementUtil;
import org.junit.jupiter.api.*;


@Tag("browser")
public class BrowserTest {
    private WebElementUtil elementUtil;
    private HomePage homePage;
    private ChannelPage channelPage;
    private VideoPage videoPage;
    private WebDriverWrapper driver;

    @BeforeAll
    public static void init() {

    }

    @BeforeEach
    public void initEach() {
        driver = new WebDriverWrapper(ConfigLoader.getBrowser());
        elementUtil = driver.createUtil();
        homePage = elementUtil.pageFactory(HomePage.class);
        channelPage = elementUtil.pageFactory(ChannelPage.class);
        videoPage = elementUtil.pageFactory(VideoPage.class);

    }

    @AfterEach
    public void cleanup() {
        driver.quit();
    }

    @Test
    @DisplayName("Search function test")
    void searchTest() {
        elementUtil.get(ConfigLoader.getBaseUrl());
        elementUtil.click(homePage.rejectCookiesBtn);
        elementUtil.click(homePage.search);
        elementUtil.sendKeys(homePage.search, "eurovision");
    }

    @Test
    @DisplayName("Click cookie reject button")
    void cookiesReject() {
        elementUtil.get(ConfigLoader.getBaseUrl());
        elementUtil.click(homePage.rejectCookiesBtn);//
    }

    @Test
    @DisplayName("Check subscribers count")
    void subscribers() throws Exception {
        int requestSubscribers = Request.getSubscribersCount(Request.sendGetRequest("https://youtube.googleapis.com/youtube/v3/channels?part=statistics&forHandle=as29nitate&key=" + "AIzaSyD4JZX8IX-l9vyDih7Qt_7hGozwbt6imZs"));
        elementUtil.get(ConfigLoader.getBaseUrl());
        elementUtil.click(homePage.rejectCookiesBtn);
        elementUtil.get(ConfigLoader.getBaseUrl()+"/@as29nitate");
        int pageSubscribers = Integer.parseInt(channelPage.subscriberCount.getText().split(" ")[0]);
        Assertions.assertEquals(requestSubscribers, pageSubscribers);

    }

    @Test
    @DisplayName("Check video page")
    void checkVideo() {
        elementUtil.get(ConfigLoader.getBaseUrl());
        elementUtil.click(homePage.rejectCookiesBtn);
        elementUtil.get(ConfigLoader.getBaseUrl()+"/watch?v=sDZTdvbJkPM");
        Assertions.assertTrue(videoPage.channelName.isDisplayed());
        Assertions.assertTrue(videoPage.channelName.getText().contains("Democracy Now!"));
        Assertions.assertTrue(videoPage.title.getText().contains("Top U.S. & World Headlines — February 19, 2025"));
        Assertions.assertTrue(videoPage.bottomRow.isDisplayed());
    }
}

