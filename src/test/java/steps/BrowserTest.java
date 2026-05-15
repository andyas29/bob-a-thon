package steps;

import org.example.pages.ChannelPage;
import org.example.pages.VideoPage;
import org.example.requests.Request;
import org.example.utils.ConfigLoader;
import org.example.utils.WebDriverWrapper;
import org.example.pages.HomePage;
import org.example.utils.WebElementUtil;
import org.junit.jupiter.api.*;

/**
 * Test class containing browser-based automated tests for YouTube functionality.
 * <p>
 * This class uses Selenium WebDriver to perform end-to-end testing of YouTube
 * features including search, cookie management, subscriber count validation,
 * and video page verification. Tests combine UI interactions with API validation
 * to ensure data consistency.
 * </p>
 * <p>
 * All tests are tagged with "browser" for selective execution and follow the
 * Page Object Model pattern for maintainability.
 * </p>
 *
 * @author Andrei Stoica
 * @version 1.0
 */
@Tag("browser")
public class BrowserTest {
    private WebElementUtil elementUtil;
    private HomePage homePage;
    private ChannelPage channelPage;
    private VideoPage videoPage;
    private WebDriverWrapper driver;

    /**
     * Static initialization method executed once before all tests in this class.
     * <p>
     * Currently empty but can be used for one-time setup operations.
     * </p>
     */
    @BeforeAll
    public static void init() {

    }

    /**
     * Initialization method executed before each test method.
     * <p>
     * Sets up a fresh WebDriver instance and initializes all page objects
     * to ensure test isolation and independence.
     * </p>
     */
    @BeforeEach
    public void initEach() {
        driver = new WebDriverWrapper(ConfigLoader.getBrowser());
        elementUtil = driver.createUtil();
        homePage = elementUtil.pageFactory(HomePage.class);
        channelPage = elementUtil.pageFactory(ChannelPage.class);
        videoPage = elementUtil.pageFactory(VideoPage.class);

    }

    /**
     * Cleanup method executed after each test method.
     * <p>
     * Quits the WebDriver instance to free resources and prevent memory leaks.
     * </p>
     */
    @AfterEach
    public void cleanup() {
        driver.quit();
    }

    /**
     * Tests the YouTube search functionality.
     * <p>
     * This test verifies that users can successfully interact with the search box
     * by clicking the search field and entering a search query.
     * </p>
     */
    @Test
    @DisplayName("Search function test")
    void searchTest() {
        elementUtil.get(ConfigLoader.getBaseUrl());
        elementUtil.click(homePage.search);
        elementUtil.sendKeys(homePage.search, "eurovision");
    }

    /**
     * Tests subscriber count consistency between API and UI.
     * <p>
     * This test validates that the subscriber count displayed on a YouTube channel
     * page matches the count returned by the YouTube Data API v3. This ensures
     * data consistency between the API and the user interface.
     * </p>
     *
     * @throws Exception if the API request fails or response parsing encounters an error
     */
    @Test
    @DisplayName("Check subscribers count")
    void subscribers() throws Exception {
        int requestSubscribers = Request.getSubscribersCount(Request.sendGetRequest("https://youtube.googleapis.com/youtube/v3/channels?part=statistics&forHandle=as29nitate&key=" + ConfigLoader.getApiKey()));
        elementUtil.get(ConfigLoader.getBaseUrl());
        elementUtil.get(ConfigLoader.getBaseUrl()+"/@as29nitate");
        int pageSubscribers = Integer.parseInt(channelPage.subscriberCount.getText().split(" ")[0]);
        Assertions.assertEquals(requestSubscribers, pageSubscribers);

    }

    /**
     * Tests the video page elements and content.
     * <p>
     * This test verifies that a YouTube video page displays correctly by checking:
     * <ul>
     *   <li>Channel name is displayed and contains expected text</li>
     *   <li>Video title is displayed and contains expected text</li>
     *   <li>Bottom row controls are visible</li>
     * </ul>
     * </p>
     */
    @Test
    @DisplayName("Check video page")
    void checkVideo() {
        elementUtil.get(ConfigLoader.getBaseUrl());
        elementUtil.get(ConfigLoader.getBaseUrl()+"/watch?v=sDZTdvbJkPM");
        Assertions.assertTrue(videoPage.channelName.isDisplayed());
        Assertions.assertTrue(videoPage.channelName.getText().contains("Democracy Now!"));
        Assertions.assertTrue(videoPage.title.getText().contains("Top U.S. & World Headlines — February 19, 2025"));
        Assertions.assertTrue(videoPage.bottomRow.isDisplayed());
    }
}

