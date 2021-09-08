package controllers.ui;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static support.Variables.*;

public class UIActions {
    private static final Logger LOGGER = LoggerFactory.getLogger(UIActions.class);
    private WebDriver webDriver;

    public UIActions(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public WebElement findElement(By locator) {
        LOGGER.info("Finding element " + locator.toString());
        return webDriver.findElement(locator);
    }

    public List<WebElement> findElements(By locator) {
        LOGGER.info("Finding elements " + locator.toString());
        return webDriver.findElements(locator);
    }

    public String getElementText(By locator) {
        LOGGER.info("Getting txt from element " + locator.toString());
        return findElement(locator).getText();
    }

    public String getElementText(WebElement webElement) {
        LOGGER.info("Getting txt from element " + webElement.toString());
        return webElement.getText();
    }

    public void sendKeys(By locator, String text) {
        LOGGER.info("Sending keys to element " + locator.toString());
        findElement(locator).sendKeys(text);
    }

    public void click(By locator) {
        LOGGER.info("Clicking element " + locator.toString());
        findElement(locator).click();
    }

    public void clickDOMElement(By locator) {
        LOGGER.info("Clicking element " + locator.toString());
        WebElement element = findElement(locator);
        JavascriptExecutor executor = (JavascriptExecutor) webDriver;
        executor.executeScript("arguments[0].click();", element);
    }

    public boolean elementIsDisplayed(By locator) {
        try {
            LOGGER.info("Verifying if " + locator.toString() + " is displayed");
            return findElement(locator).isDisplayed();
        } catch (NoSuchElementException e) {
            LOGGER.info("Element " + locator.toString() + "is not displayed");
            return false;
        }
    }

    public void visitSite(String url) {
        LOGGER.info("Visiting site " + url);
        webDriver.get(url);
    }

    public void shouldSee(By locator) {
        try {
            LOGGER.info("Should see element " + locator.toString());
            WebDriverWait wait = new WebDriverWait(webDriver, STANDARD_TIMEOUT_SECONDS);
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            LOGGER.info("Element seen");
            Assertions.assertTrue(true);
        } catch (TimeoutException e) {
            LOGGER.error("Element unreachable");
            Assertions.assertTrue(false);
        }
    }

    public void shouldBeClickable(By locator) {
        try {
            LOGGER.info("Element should be clickable " + locator.toString());
            WebDriverWait wait = new WebDriverWait(webDriver, STANDARD_TIMEOUT_SECONDS);
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            LOGGER.info("Element clicked");
            Assertions.assertTrue(true);
        } catch (TimeoutException e) {
            LOGGER.error("Element unreachable");
            Assertions.assertTrue(false);
        }
    }

    public void shouldBeInvisible(By locator) {
        try {
            LOGGER.info("Element should be invisible " + locator.toString());
            WebDriverWait wait = new WebDriverWait(webDriver, STANDARD_TIMEOUT_SECONDS);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
            LOGGER.info("Element is invisible");
            Assertions.assertTrue(true);
        } catch (TimeoutException e) {
            LOGGER.error("Element unreachable");
            Assertions.assertTrue(false);
        }
    }

    public void textShouldBeEqualsTo(By locator, String expectedText) {
        String actualText = getElementText(locator).trim();
        Assertions.assertEquals(expectedText, actualText);
    }

    public void textShouldBeEqualsToIgnoringCase(By locator, String expectedText) {
        String actualText = getElementText(locator).trim();
        Assertions.assertEquals(expectedText.toUpperCase(), actualText.toUpperCase());
    }

    public void scrollDownToElement(By locator) {
        LOGGER.info("Scrolling down to element " + locator);
        WebElement element = findElement(locator);
        JavascriptExecutor executor = (JavascriptExecutor) webDriver;
        executor.executeScript("arguments[0].scrollIntoView();", element);
    }

    public void scrollToFooter() {
        LOGGER.info("Scrolling down to the bottom of the page");
        JavascriptExecutor executor = (JavascriptExecutor) webDriver;
        executor.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }
}
