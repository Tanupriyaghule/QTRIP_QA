package qtriptest.pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.concurrent.TimeoutException;

public class LoginPage {
    private static RemoteWebDriver driver;
    @FindBy(name = "email")
    private static WebElement emailTextBox;

    @FindBy(name = "password")
    private static WebElement passwordTextBox;

    @FindBy(xpath = "//a[text()='Login Here']")
    private static WebElement loginButton;

    public LoginPage(RemoteWebDriver driver) {
        LoginPage.driver = driver;
        // PageFactory.initElements(driver, this);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
    }

    public static boolean checkLoginPageNavigation() {
        return driver.getCurrentUrl().contains("https://qtripdynamic-qa-frontend.vercel.app/pages/login");
    }
    public static void login(String email, String password) throws TimeoutException {
        try {
            System.out.println("Logging in with credentials...");

            if (email != null) {
                emailTextBox.sendKeys(email);
            }

            if (password != null) {
                passwordTextBox.sendKeys(password);
            }

            loginButton.click();

            WebDriverWait wait = new WebDriverWait(driver, 20);

            // Wait until a more reliable condition (e.g., presence of an element on the next page)
            // You might want to customize this condition based on your application's behavior
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1[text()='Welcome to QTrip']")));
            System.out.println("Login successful!");
        } catch (Exception e) {
            System.out.println("Exception occurred during login: " + e.getMessage());
            e.printStackTrace();
        }
    }
    }
