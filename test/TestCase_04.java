package qtriptest.tests;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.pages.AdventureDetailsPage;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HistoryPage;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
public class TestCase_04 extends BaseTest {
    private static ExtentTest test;
    private static ExtentReports report;
    private HomePage homePage;
    @BeforeSuite(alwaysRun = true)
    public void createDriver() throws MalformedURLException {
        driver = DriverSingleton.getInstanceOfSingletonBrowserClass().getDriver();
        report = new ExtentReports(System.getProperty("user.dir") + "/src/test/java/qtriptest/tests/ExtentReport.html", true);
        test = report.startTest("QTripbookcityhistory");
    }
    @Test(description = "Booking History Flow", priority = 4,groups = "4", dataProvider = "qtripData1", dataProviderClass = DP.class)
    public void TestCase04(String NewUserName, String Password, String dataset1, String dataset2, String dataset3) throws InterruptedException {
        try {
            // Initialize page objects
            new HomePage(driver);
            AdventurePage adventurePage = new AdventurePage(driver);
            AdventureDetailsPage adventureDetailsPage = new AdventureDetailsPage(driver);
            new HistoryPage(driver);
            new LoginPage(driver);
            new RegisterPage(driver);

            String email = NewUserName;
            String password = Password;
            driver.get("https://qtripdynamic-qa-frontend.vercel.app/");
            homePage.selectOptionOnHomePage("register");

            String confirmPassword = password;
            RegisterPage.registerANewUser(email, password, confirmPassword, true);
            Thread.sleep(5000);

            LoginPage.checkLoginPageNavigation();
            LoginPage.login(email, password);

            // First Booking
            String[] data1 = dataset1.split(";");
            bookAdventure(adventurePage, adventureDetailsPage, data1);

            // View Booking History
            HistoryPage.viewHistory();

            // Second Booking
            String[] data2 = dataset2.split(";");
            bookAdventure(adventurePage, adventureDetailsPage, data2);

            // View Booking History
            HistoryPage.viewHistory();

            // Third Booking
            String[] data3 = dataset3.split(";");
            bookAdventure(adventurePage, adventureDetailsPage, data3);

            try{// View Booking History
            HistoryPage.viewHistory();
            test.log(LogStatus.PASS, test.addScreenCapture(capture(driver)) + "Booking history Pass");
            }catch (AssertionError e) {
                // Log the exception and rethrow it
                test.log(LogStatus.FAIL, "Booking history failed");
            }
        } catch (Exception e) {
            // Log the exception and rethrow it
            e.printStackTrace();
            try {
                throw e;
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }

    private void bookAdventure(AdventurePage adventurePage, AdventureDetailsPage adventureDetailsPage, String[] data) throws InterruptedException {
        String searchCity = data[0];
        adventurePage.searchCity(searchCity);
        Thread.sleep(3000);

        String adventureName = data[1];
        adventureDetailsPage.searchAdventure(adventureName);
        Thread.sleep(3000);

        String guestName = data[2];
        String date = data[3];
        String count = data[4];
        adventureDetailsPage.bookAdventure(guestName, date, count);
        Thread.sleep(3000);
        adventureDetailsPage.checkIfBookingSuccessful();
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
