package qtriptest.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AdventurePage {
    static WebDriver driver;

    @FindBy(xpath = "//*[@id='autocomplete']")
    private WebElement cityName;

    public AdventurePage(WebDriver driver) {
        AdventurePage.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
    }
    public boolean searchCity(String searchCity) {
        try {
            WebElement searchBar = driver.findElement(By.xpath("//*[@id='autocomplete']"));
            searchBar.clear();
            searchBar.sendKeys(searchCity);
            driver.findElement(By.xpath("//*[@id='results']")).click();
            Thread.sleep(3000);
            return true;
        } catch (Exception e) {
            System.out.println("No city found: " + e.getMessage());
            return false;
        }
    }

    public void DurationFilter(String DurationFilter) {
        driver.get("https://qtripdynamic-qa-frontend.vercel.app/pages/adventures/");
        WebElement durationDropdown = driver.findElement(By.xpath("//*[@id='duration-select']"));
        durationDropdown.click();
        new Select(durationDropdown).selectByVisibleText(DurationFilter);
    }

    public void CategoryFilter(String CategoryFilter) {
        driver.get("https://qtripdynamic-qa-frontend.vercel.app/pages/adventures/");
        WebElement categoryDropdown = driver.findElement(By.xpath("//*[@id='category-select']"));
        categoryDropdown.click();
        new Select(categoryDropdown).selectByVisibleText(CategoryFilter);
    }

    public boolean verifyDataDisplayedAfterFilter(String Category) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement dataSection = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='category-section']")));
        return dataSection.isDisplayed();
    }

    public void clear() {
        driver.get("https://qtripdynamic-qa-frontend.vercel.app/pages/adventures/");
        driver.findElement(By.xpath("//div[@class='ms-3' and @style='color: #0645ad; cursor: pointer' and @onclick='clearDuration(event)']")).click();
        driver.findElement(By.xpath("//div[@class='ms-3' and @style='color: #0645ad; cursor: pointer' and @onclick='clearCategory(event)']")).click();
    }

    public boolean verifyAllRecordsDisplayedAfterFilter(String ExpectedFilteredResults, String ExpectedUnFilteredResults) {
        driver.get("https://qtripdynamic-qa-frontend.vercel.app/pages/adventures/");
        WebElement dataElement = driver.findElement(By.xpath("//*[@id='data']"));
        String actualData = dataElement.getText();
        return actualData.contains(ExpectedFilteredResults) || actualData.contains(ExpectedUnFilteredResults);
    }
}
