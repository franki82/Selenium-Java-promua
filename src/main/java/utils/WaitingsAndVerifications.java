package utils;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class WaitingsAndVerifications {
    public void waitForVisibilityElement(WebDriver driver, Integer timeoutValue, WebElement element, String elementName) {
        Wait<WebDriver> wait = new WebDriverWait(driver, timeoutValue)
                .withMessage(elementName + " is not visible after wait: " + timeoutValue + " seconds ");
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForElementWithText(WebDriver driver, Integer timeoutValue, WebElement element, String filteredText, String elementName) {
        Wait<WebDriver> wait = new WebDriverWait(driver, timeoutValue)
                .withMessage(elementName + " is not visible after wait: " + timeoutValue + " seconds ");
        wait.until(ExpectedConditions.textToBePresentInElement(element, filteredText));
    }

    public void waitForElementWithValueText(WebDriver driver, Integer timeoutValue, WebElement element, String filteredText, String elementName) {
        Wait<WebDriver> wait = new WebDriverWait(driver, timeoutValue)
                .withMessage(elementName + " is not visible after wait: " + timeoutValue + " seconds ");
        wait.until(ExpectedConditions.textToBePresentInElementValue(element, filteredText));
    }


    public void waitForClickableElementState(WebDriver driver, Integer timeoutValue, WebElement element, String elementName) {
        Wait<WebDriver> wait = new WebDriverWait(driver, timeoutValue)
                .withMessage(elementName + " is not clickable after wait: " + timeoutValue + " seconds ");
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitForInvisibility(WebDriver driver, Integer timeoutValue, WebElement element, String elementName) {
        try {
            Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(timeoutValue))
                    .withMessage(elementName + " is still visible: " + timeoutValue + " seconds ");
            wait.until(ExpectedConditions.invisibilityOf(element));
        } catch (NoSuchElementException e){
            //use if element removed before start verification
        }
    }

    public void WaitForLoad(int seconds, String partUrl, WebDriver driver) {
        Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(seconds))
                .ignoring(TimeoutException.class);
        wait.until(ExpectedConditions.urlContains(partUrl));
    }

    public void startHardTimeout(int seconds) throws Exception {
        TimeUnit.SECONDS.sleep(seconds);
    }

    public void printMessage(String myMessage) {
        System.out.println(myMessage);
    }


    public void verifyStrings(String actual, String expected) {
        Assert.assertEquals(actual.replaceAll("\\s+", ""), expected.replaceAll("\\s+", ""),
                "Failed verification: actual string '" + actual + "' not equal to expected string '" + expected
                        + "'. \n Details: ");
    }

    public void verifyTrue(boolean condition, String myValidationMessage) {
        Assert.assertTrue(condition, "Failed boolean (TRUE) verification. Details: " + myValidationMessage + ". Technical details:");
    }

    public void verifyNotEqualStrings(String actual, String expected) {
        Assert.assertNotEquals(actual.replaceAll("\\s+", ""), expected.replaceAll("\\s+", ""),
                "Failed verification: actual string '" + actual + "' equal to expected string '" + expected
                        + "'. \n Details: ");
    }

    public boolean verifyIsElementPresent(WebElement elementToVerify){
        try{
            elementToVerify.getSize();
            return true;
        } catch (Throwable e){
            return false;
        }
    }

    public boolean verifyIsElementPresent(List<WebElement> elementToVerify){
        try{
            return !elementToVerify.isEmpty();
        } catch (Throwable e){
            return false;
        }
    }


}
