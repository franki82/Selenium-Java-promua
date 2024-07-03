package objects;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import utils.Utils;
import utils.WaitingsAndVerifications;

import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class DriverInitiation extends WaitingsAndVerifications {
    public WebDriver driver;
    public Integer longTimeout;
    String pathToLocalLog = "./src/main/resources/log/";
    String pathToProperty = "./src/main/resources/properties/promua.properties";
    public String urlToLoginPage;
    String gridUrl;
    String browserName;

    public DriverInitiation(String browserName) throws Exception {
        Utils utils = new Utils();
        this.browserName = browserName;
        if (browserName == null){
            browserName = "chrome";
        }
        utils.loadPropertyFile(pathToProperty);
        longTimeout = Integer.valueOf(utils.getProperty("longtimeout"));
        int waitLoadingPageTimeout = Integer.parseInt(utils.getProperty("loadingPageTimeout"));
        int waitLoadingScriptsTimeout = Integer.parseInt(utils.getProperty("loadingScriptsTimeout"));
        if (System.getProperty("environmentName") != null && !System.getProperty("environmentName").isEmpty()){
            urlToLoginPage = "https://" + System.getProperty("environmentName");
            System.out.println("Select environment " + urlToLoginPage);
        } else {
            urlToLoginPage = utils.getProperty("url");
            System.out.println("Select qa environment (default, not set environmentName in mvn command)");
        }
        String gridSystemVariable = System.getenv("GRID_URL");
        if (gridSystemVariable == null) {
            System.out.println("System Variable GRID_URL not exist. Try to use grid url from properties file");
            gridUrl = utils.getProperty("gridurl");
        } else {
            System.out.println("System Variable GRID_URL: " + gridSystemVariable);
            gridUrl = gridSystemVariable;
        }
        switch (browserName){
            case "firefox":
                System.out.println("You selected Firefox browser");
                FirefoxOptions optionsFirefox = new FirefoxOptions();
                optionsFirefox.addArguments("--no-sandbox");
                optionsFirefox.addArguments("--disable-dev-shm-usage");
                driver = new RemoteWebDriver(new URL(gridUrl), optionsFirefox);
                break;
            case "chrome":
            default:
                System.out.println("You selected Chrome browser");
                HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
                chromePrefs.put("profile.default_content_settings.popups", 0);
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--no-sandbox");
                options.setExperimentalOption("prefs", chromePrefs);
                DesiredCapabilities cap = DesiredCapabilities.chrome();
                cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
                cap.setCapability(ChromeOptions.CAPABILITY, options);
                driver = new RemoteWebDriver(new URL(gridUrl), cap);
                break;
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().setScriptTimeout(waitLoadingScriptsTimeout, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(waitLoadingPageTimeout, TimeUnit.SECONDS);
    }

    public void quitDriver() { driver.quit(); }

    public String getPathToLocalLog() {
        return pathToLocalLog;
    }

}
