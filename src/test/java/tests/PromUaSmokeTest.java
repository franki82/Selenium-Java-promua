package tests;

import objects.ElementsInitiation;
import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.util.List;

public class PromUaSmokeTest extends ElementsInitiation {

    @BeforeClass
    @Parameters({"browser"})
    public void initiateTest(@Optional String browserName) throws Exception {
        mainPage.openDefaultMainPage(initiation.urlToLoginPage);
    }

    @BeforeMethod
    public void refreshPageBeforeTest(){
        mainPage.refreshPage(initiation.urlToLoginPage);
    }

    @AfterMethod
    public void compareTestResults(ITestResult testResult) throws Exception {
        utils.getScreenshotAndWriteLogAfterException(driver, initiation.getPathToLocalLog(), testResult);
    }

    @AfterClass
    public void finishTest(){
        initiation.quitDriver();
    }

    @DataProvider(name = "smokeSearchData")
    public Object[][] getSmokeSearchData(){
        return new Object[][]{{"18650"}, {"21700"}};
    }



    @Test(priority = 0, dataProvider = "smokeSearchData")
    public void testSmokeSearchVerification(String searchString) {
        initiation.printMessage("Testcase: open prom.ua, verify main elements, simple search, verify search results");
        mainPage.verifyDefaultMainPage();
        search.simpleSearch(searchString);
        List<WebElement> productPlates = searchResults.getProductPlates();
        initiation.verifyTrue(!productPlates.isEmpty(), "Search results are empty");
        for (WebElement productPlate : productPlates){
            String currentDescription = searchResults.getProductDescription(productPlate);
            String currentPrice = searchResults.getProductPrice(productPlate);
            initiation.printMessage("Product description: " + currentDescription);
            initiation.printMessage("Product price: " + currentPrice);
            initiation.verifyTrue(currentDescription.contains(searchString), "Description is not contains " + searchString);
            initiation.verifyTrue(!currentPrice.isEmpty(), "Price is empty");
        }
    }
}
