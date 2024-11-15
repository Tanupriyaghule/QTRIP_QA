package qtriptest.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
public class HistoryPage {
    private static RemoteWebDriver driver;

    private final static By viewHistoryLinkLocator = By.xpath("//*[@id='reserved-banner']/a/strong");

    public HistoryPage(WebDriver driver) {
        HistoryPage.driver = (RemoteWebDriver) driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
    }
    public static void viewHistory() {
        driver.findElement(viewHistoryLinkLocator).click();
    }
  
}
