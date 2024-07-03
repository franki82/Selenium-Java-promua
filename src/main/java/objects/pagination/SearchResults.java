package objects.pagination;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.WaitingsAndVerifications;

import java.util.List;

public class SearchResults extends WaitingsAndVerifications {
    WebDriver driver;
    Integer longTimeout;

    public SearchResults(WebDriver driver, Integer longTimeout) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.longTimeout = longTimeout;
    }

    @FindBy(xpath = "//div[@data-qaid='product_gallery']")
    private WebElement searchResultsElement;

    @FindBy(xpath = "//div[@data-qaid='product_gallery']/div[@data-product-id]")
    private List<WebElement> searchResultsItems;

    @FindBy(xpath = "//div[@data-qaid='pagination']")
    private WebElement paginationBlockElement;

    private String productDescriptionXpath = "div//span[@data-qaid='product_name']";
    private String productPriceXpath = "div//div[@data-qaid='product_price']";
    private String productBuyButtonXpath = "div//a[@data-qaid='buy-button']";

    public List<WebElement> getProductPlates() {
        waitForVisibilityElement(driver, longTimeout, searchResultsElement, "Search Results");
        Actions actions = new Actions(driver);
        actions.moveToElement(paginationBlockElement).build().perform();
        waitForVisibilityElement(driver, longTimeout, paginationBlockElement, "Pagination block");
        return searchResultsItems;
    }

    public String getProductDescription(WebElement productPlate){
        return productPlate.findElement(By.xpath(productDescriptionXpath)).getText().trim();
    }

    public String getProductPrice(WebElement productPlate){
        return productPlate.findElement(By.xpath(productPriceXpath)).getAttribute("data-qaprice").trim();
    }

    public void clickToProductBuyButton(WebElement productPlate){
        productPlate.findElement(By.xpath(productBuyButtonXpath)).click();
    }
}
