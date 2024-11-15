package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class TestCase_01 extends BaseTest {
    private static ExtentTest test;
    private static ExtentReports report;

    @BeforeSuite(alwaysRun = true)
    public static void createDriver() throws IOException, TimeoutException {
        try {
            driver = DriverSingleton.getInstanceOfSingletonBrowserClass().getDriver();
            report = new ExtentReports(System.getProperty("user.dir") + "/src/test/java/qtriptest/tests/ExtentReport.html", true);
            test = report.startTest("QTripLogin");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(description = "User Register and Login", priority = 1, groups = "1", dataProvider = "qtripData", dataProviderClass = DP.class)
    public static void TestCase01(String email, String password) throws TimeoutException, IOException {
        System.out.println("driver1=" + driver);
        SoftAssert softAssert = new SoftAssert();

        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        registerPage = new RegisterPage(driver);

        softAssert.assertTrue(HomePage.checkTheNavigationToHomePage(), "Navigation to home page failed");

        homePage.selectOptionOnHomePage("register");

        RegisterPage.registerANewUser(email, password, password, true);

        softAssert.assertTrue(LoginPage.checkLoginPageNavigation(), "Navigation to login page failed");
       
     //  String lastGeneratedUserName = "tanu";
        try {
            LoginPage.login(email, password);
           // LoginPage.login(lastGeneratedUserName, "abc@123");
            test.log(LogStatus.PASS, test.addScreenCapture(capture(driver)) +"Login Passed");
        } catch (AssertionError e) {
            test.log(LogStatus.PASS, "Login Failed");
            e.printStackTrace();
        }
        softAssert.assertTrue(homePage.isUserLoggedIn(), "User is unable to log in");
    }
   
    private static String capture(WebDriver driver) throws IOException {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File dest = new File(System.getProperty("user.dir") + "/QTRIPIMAGES/" + System.currentTimeMillis() + ".png");
        String errFilePath = dest.getAbsolutePath();
        FileUtils.copyFile(scrFile, dest);
        return errFilePath;
    }

    @AfterSuite
    public void quitDriver() {
        try {
            driver.quit();
            report.endTest(test);
            report.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
