package qtriptest.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class AdventureDetailsPage {
    private static RemoteWebDriver driver;

    @FindBy(xpath = "//*[@id='autocomplete']")
    private WebElement cityName;

    public AdventureDetailsPage(WebDriver driver) {
        AdventureDetailsPage.driver = (RemoteWebDriver) driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
    }

    public void searchAdventure(String AdventureName) {
        driver.get("https://qtripdynamic-qa-frontend.vercel.app/pages/adventures/");
        WebDriverWait wait = new WebDriverWait(driver, 10);

        try {
            By searchInputLocator = By.xpath("//*[@id='search-adventures']");
            WebElement searchInput = wait.until(ExpectedConditions.elementToBeClickable(searchInputLocator));
            searchInput.clear();
            searchInput.sendKeys(AdventureName);
            Thread.sleep(2000);

            List<WebElement> searchResultsContainers = driver.findElements(By.xpath("//div[@class='search-results']"));

            if (searchResultsContainers.isEmpty()) {
                System.out.println("No Adventure Found");
            } else {
                WebElement firstSearchResult = searchResultsContainers.get(0);
                firstSearchResult.click();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void bookAdventure(String GuestName, String Date, String count) {
        driver.get("https://qtripdynamic-qa-frontend.vercel.app/pages/adventures/detail/?adventure");

        WebElement nameInput = driver.findElement(By.xpath("//*[@id='myForm']/input[1]"));
        nameInput.sendKeys(GuestName);

        WebElement dateInput = driver.findElement(By.xpath("//*[@id='myForm']/input[2]"));
        dateInput.sendKeys(Date);

        WebElement countInput = driver.findElement(By.xpath("//*[@id='myForm']/div[1]/div[2]/input"));
        countInput.sendKeys(count);

        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement reserveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='myForm']/button")));
        reserveButton.click();
    }

    public void checkIfBookingSuccessful() {
        WebElement reservationLink = new WebDriverWait(driver, 10)
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@href, '../reservations/')]/strong")));
        reservationLink.click();

        driver.get("https://qtripdynamic-qa-frontend.vercel.app/pages/adventures/reservations/");
        WebElement transactionIDElement = new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='reservation-table']/tr[1]/th")));
        String transactionID = transactionIDElement.getText();
        System.out.println("Transaction ID: " + transactionID);

        WebElement cancelButton = driver.findElement(By.xpath("//button[@id='e3ad11b52229452d' and @class='cancel-button']"));
        cancelButton.click();

        driver.navigate().refresh();
    }
}
