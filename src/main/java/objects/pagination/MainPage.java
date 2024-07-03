package objects.pagination;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.WaitingsAndVerifications;

import java.util.List;

public class MainPage extends WaitingsAndVerifications {
    WebDriver driver;
    Integer longTimeout;

    public MainPage(WebDriver driver, Integer longTimeout) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.longTimeout = longTimeout;
    }

    @FindBy(xpath = "//div[@data-qaid='promo_panel']")
    private WebElement promuaPromoPanel;

    @FindBy(xpath = "//a[@title='prom.ua']/img")
    private WebElement promuaMainLogo;

    @FindBy(xpath = "//div[@data-qaid='menu_preview']")
    private WebElement menuPreviewElement;

    @FindBy(xpath = "//div[@data-qaid='menu_preview']//li/a")
    private List<WebElement> menuPreviewItems;

    @FindBy(xpath = "//a[@data-qaid='banner_link']/img")
    private WebElement actualBannerImage;

    @FindBy(xpath = "//div[@data-qaid='recommended_categories']")
    private WebElement recommendedCategoriesElement;

    @FindBy(xpath = "//div[@data-qaid='recommended_categories']//li//picture")
    private List<WebElement> recommendedCategoriesImages;

    @FindBy(xpath = "//div[@data-qaid='recommended_categories']//li//span/a")
    private List<WebElement> recommendedCategoriesDescriptions;

    @FindBy(xpath = "//div[@data-qaid='personal_feed_block']")
    private WebElement personalFeedElement;

    @FindBy(xpath = "//div[@data-qaid='personal_feed_block']//picture")
    private List<WebElement> personalFeedImages;

    @FindBy(xpath = "//div[@data-qaid='personal_feed_block']//a/span")
    private List<WebElement> personalFeedDescriptions;

    @FindBy(xpath = "//div[@data-qaid='personal_feed_block']//a[@role='button']")
    private List<WebElement> personalFeedBuyButtons;

    @FindBy(xpath = "//div[@data-qaid='footer_links']")
    private WebElement promuaFooterElement;

    public void openDefaultMainPage(String linkToSite) throws Exception {
        printMessage("Open Prom.ua STARTED");
        try {
            driver.get(linkToSite);
            waitForVisibilityElement(driver, longTimeout, promuaMainLogo, "Main logo");
            verifyIsElementPresent(promuaFooterElement);
            printMessage("Open Prom.ua - FINISHED");
        } catch (Exception e){
            printMessage("Open Prom.ua failed");
            driver.quit();
            throw e;
        }
    }

    public void verifyDefaultMainPage(){
        waitForVisibilityElement(driver, longTimeout, promuaPromoPanel, "Promo panel");
        waitForVisibilityElement(driver, longTimeout, promuaMainLogo, "Main logo");
        waitForVisibilityElement(driver, longTimeout, menuPreviewElement, "Menu preview");
        waitForVisibilityElement(driver, longTimeout, actualBannerImage, "Banner image");
        waitForVisibilityElement(driver, longTimeout, recommendedCategoriesElement, "Recommended Categories");
        waitForVisibilityElement(driver, longTimeout, personalFeedElement, "Personal Feed");
        verifyIsElementPresent(promuaFooterElement);
        printMessage("Main elements verified");
    }

    public void refreshPage(){
        driver.navigate().refresh();
        waitForVisibilityElement(driver, longTimeout, promuaMainLogo, "Main logo");
        printMessage("Refreshed prom.ua");
    }

    public void refreshPage(String linkToSite){
        driver.get(linkToSite);
        waitForVisibilityElement(driver, longTimeout, promuaMainLogo, "Main logo");
        printMessage("Refreshed prom.ua");
    }
}
