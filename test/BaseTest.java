package qtriptest.tests;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import qtriptest.DriverSingleton;
import qtriptest.pages.AdventureDetailsPage;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HistoryPage;
import qtriptest.pages.HomePage;
import qtriptest.pages.RegisterPage;
import java.net.MalformedURLException;
import qtriptest.pages.LoginPage;
public class BaseTest {
    public static RemoteWebDriver driver;
    public static HomePage homePage;
    public static RegisterPage registerPage;
    public static LoginPage loginPage;
    public AdventurePage adventurePage;
    public AdventureDetailsPage adventureDetailsPage;
    public HistoryPage historyPage;
    @BeforeSuite(alwaysRun = true)
    public void config() throws MalformedURLException {
        driver = DriverSingleton.getInstanceOfSingletonBrowserClass().getDriver();
        driver.get("https://qtripdynamic-qa-frontend.vercel.app");
        homePage = new HomePage(driver);
        registerPage = new RegisterPage(driver);
        loginPage = new LoginPage(driver);
    adventurePage = new AdventurePage(driver);
    adventureDetailsPage = new AdventureDetailsPage(driver);
    historyPage=new HistoryPage(driver);
    }
    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        // Add any necessary cleanup code here
    }
}
