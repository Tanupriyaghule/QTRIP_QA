package qtriptest;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumWrapper {
    public void click(WebElement we, WebDriver driver) throws InterruptedException {
       JavascriptExecutor js=(JavascriptExecutor)driver;
       js.executeScript("arguments[0].style.border='2px solid red'",we);
       Thread.sleep(6000);
       we.click();
    }
    public static boolean sendKeys(WebElement inputBox, String keysToSend, WebDriver driver) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            WebElement visibleElement = wait.until(ExpectedConditions.visibilityOf(inputBox));
            visibleElement.clear();
            visibleElement.sendKeys("hii");
            return true;
        } catch (Exception e) {
                return false;
        }
    }
    public static boolean navigate(WebDriver driver, String url) {
        try {
            driver.navigate().to("https://qtripdynamic-qa-frontend.vercel.app/");
            return true;
        } catch (Exception e) {
          
            return false;
        }
    }
    public static WebElement findElementWithRetry(WebDriver driver, By by, int retryCount) throws Exception {
        WebElement element = null;
        int attempts = 0;

        while (attempts < retryCount) {
            try {
                element = driver.findElement(by);
                // If the element is found, break out of the loop
                break;
            } catch (StaleElementReferenceException e) {
                // Handle StaleElementReferenceException, retrying
            } catch (Exception e) {
                // Handle other exceptions or log them
            }
            // Increment the retry count and wait for a short duration before retrying
            attempts++;
            Thread.sleep(1000);
        }

        if (element == null) {
            throw new Exception("Element not found after " + retryCount + " attempts");
        }

        return element;
    }
}

