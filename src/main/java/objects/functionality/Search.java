package objects.functionality;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.WaitingsAndVerifications;

import java.util.List;

public class Search extends WaitingsAndVerifications {
    WebDriver driver;
    Integer longTimeout;

    public Search(WebDriver driver, Integer longTimeout) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.longTimeout = longTimeout;
    }

    @FindBy(xpath = "//input[@name='search_term']")
    private WebElement searchInput;

    @FindBy(xpath = "//button[@data-qaid='search_by_voice']")
    private WebElement searchByVoiceButton;

    @FindBy(xpath = "//button[@data-qaid='search_btn' and @disabled]")
    private WebElement startSearchButtonNotActive;

    @FindBy(xpath = "//button[@data-qaid='search_btn' and not(@disabled)]")
    private WebElement startSearchButtonActive;

    @FindBy(xpath = "//button[@data-qaid='clear_search']")
    private WebElement clearSearchButton;

    @FindBy(xpath = "//a[@data-qaid='search_term']")
    private List<WebElement> advancedSearchSamplesItems;

    @FindBy(xpath = "//div[@data-qaid='backdrop']//following-sibling::div/div/ul")
    private WebElement searchExpand;

    @FindBy(xpath = "//button[@data-qaid='multi_search_btn']")
    private WebElement findFromOneSellerButton;

    @FindBy(xpath = "//div[@data-qaid='backdrop']//following-sibling::div//li//ul")
    private WebElement popularProductExamplesElement;

    @FindBy(xpath = "//div[@data-qaid='backdrop']//following-sibling::div//a")
    private List<WebElement> popularProductExamplesItems;

    public void simpleSearch(String searchValue){
        waitForVisibilityElement(driver, longTimeout, searchInput, "Search Input");
        waitForVisibilityElement(driver, longTimeout, startSearchButtonNotActive, "Start Search Button (Not Active)");
        searchInput.sendKeys(searchValue);
        waitForVisibilityElement(driver, longTimeout, clearSearchButton, "Clear Search Button");
        waitForVisibilityElement(driver, longTimeout, startSearchButtonActive, "Start Search Button (Active)");
        startSearchButtonActive.click();
        //second comment
    }

}
