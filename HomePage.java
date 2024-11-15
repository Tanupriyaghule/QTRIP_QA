package qtriptest.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class HomePage {
   
    private static RemoteWebDriver driver;

    @FindBy(xpath = "//a[text()='Register']")
    private WebElement registerButton;

    @FindBy(xpath = "//a[text()='Login Here']")
    private WebElement loginHereButton;

    @FindBy(xpath = "//a[text()='Home']")
    private WebElement homeButton;

    @FindBy(xpath = "//*[@id='navbarNavDropdown']/ul/li[4]/a")
    private WebElement logoutButton;

    public HomePage(RemoteWebDriver driver) {
        HomePage.driver = driver;
        // PageFactory.initElements(driver, this);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
    }

    public void selectOptionOnHomePage(String option) {
        System.out.println(option.toLowerCase()+" ***************************");
        switch (option.toLowerCase()) {            
            case "register":
                registerButton.click();
                System.out.println("hi");
                break;
            case "login":
                loginHereButton.click();
                break;
            case "logout":
                logoutButton.click();
                break;
            case "home":
                homeButton.click();
                break;
            default:
                driver.get("https://qtripdynamic-qa-frontend.vercel.app/");
                break;
        }
    }

    public static boolean checkTheNavigationToHomePage() {
        return driver.getCurrentUrl().contains("https://qtripdynamic-qa-frontend.vercel.app");
    }
    public boolean isUserLoggedIn() {
        return !driver.getCurrentUrl().contains("/login");
    }   
}
