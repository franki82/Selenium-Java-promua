package objects;

import objects.functionality.Search;
import objects.pagination.MainPage;
import objects.pagination.SearchResults;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import utils.Utils;

public abstract class ElementsInitiation {
    public DriverInitiation initiation;
    public MainPage mainPage;
    public Search search;
    public SearchResults searchResults;
    public Utils utils;
    public WebDriver driver;
    public Integer longTimeout;

    @BeforeClass(alwaysRun = true)
    @Parameters({"browser"})
    public void primaryInitiateTest(@Optional String browserName) throws Exception {
        initiation = new DriverInitiation(browserName);
        driver = initiation.driver;
        longTimeout = initiation.longTimeout;
        mainPage = new MainPage(driver, longTimeout);
        utils = new Utils();
        search = new Search(driver, longTimeout);
        searchResults = new SearchResults(driver, longTimeout);
    }
}
