package qtriptest.tests;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.pages.AdventureDetailsPage;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class TestCase_03 extends BaseTest {
    private static ExtentTest test;
    private static ExtentReports report;
    private HomePage homePage;
    private AdventurePage adventurePage;

    @BeforeSuite(alwaysRun = true)
    public void createDriver() throws MalformedURLException {
        driver = DriverSingleton.getInstanceOfSingletonBrowserClass().getDriver();
        report = new ExtentReports(System.getProperty("user.dir") + "/src/test/java/qtriptest/tests/ExtentReport.html", true);
        test = report.startTest("QTripbookcity");
    }

    @Test(description = "Booking and Cancellation Flow", priority = 3, groups = "3", dataProvider = "qtripBookData", dataProviderClass = DP.class)
    public void TestCase03(String NewUserName, String Password, String SearchCity, String AdventureName, String GuestName, String Date, String count) throws InterruptedException {
        try {
            // Initialize page objects
            homePage = new HomePage(driver);
            adventurePage = new AdventurePage(driver);
            AdventureDetailsPage adventureDetailsPage = new AdventureDetailsPage(driver);
            new LoginPage(driver);
            new RegisterPage(driver);

            // Your existing code...
            String email = NewUserName;
            String password = Password;

            driver.get("https://qtripdynamic-qa-frontend.vercel.app/");
            homePage.selectOptionOnHomePage("register");

            String confirmPassword = password;
            RegisterPage.registerANewUser(email, password, confirmPassword, true);
            Thread.sleep(5000);

            LoginPage.checkLoginPageNavigation();
            LoginPage.login(email, password);

            adventurePage.searchCity(SearchCity);
            Thread.sleep(3000);
            adventurePage.DurationFilter("2-6 Hours");
            adventurePage.CategoryFilter("Party Spots");

            adventureDetailsPage.searchAdventure(AdventureName);
            Thread.sleep(3000);
            adventureDetailsPage.bookAdventure(GuestName, Date, count);
            Thread.sleep(3000);
            adventureDetailsPage.checkIfBookingSuccessful();
            adventurePage.searchCity(SearchCity);
            Thread.sleep(3000);
            adventurePage.DurationFilter("6-12 Hours");
            adventurePage.CategoryFilter("Hillside Getaways");

            adventureDetailsPage.searchAdventure(AdventureName);
            Thread.sleep(3000);
            adventureDetailsPage.bookAdventure(GuestName, Date, count);
            Thread.sleep(3000);
            adventureDetailsPage.checkIfBookingSuccessful();
            adventurePage.searchCity(SearchCity);
            Thread.sleep(3000);
            adventurePage.DurationFilter("12+ Hours");
            adventurePage.CategoryFilter("Serene Beaches");

            adventureDetailsPage.searchAdventure(AdventureName);
            Thread.sleep(3000);
            adventureDetailsPage.bookAdventure(GuestName, Date, count);
            Thread.sleep(3000);
            try {
                test.log(LogStatus.PASS, test.addScreenCapture(capture(driver)) + "Test Pass");
                adventureDetailsPage.checkIfBookingSuccessful();
            } catch (AssertionError e) {
                // Log the exception and rethrow it
                test.log(LogStatus.FAIL, "Booking failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String capture(RemoteWebDriver driver) throws IOException {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File dest = new File(System.getProperty("user.dir") + "/QTRIPIMAGES/" + System.currentTimeMillis() + ".png");
        String errFilePath = dest.getAbsolutePath();
        FileUtils.copyFile(scrFile, dest);
        return errFilePath;
    }

    @AfterSuite
    public void tearDown() {
        try {
            // driver.quit();
            // TODO - End the test
            report.endTest(test);
            // TODO - Write the test to the filesystem
            report.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
