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
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class TestCase_02 extends BaseTest {
    private static ExtentTest test;
    private static ExtentReports report;
    private HomePage homePage;
    private AdventurePage adventurePage;

    @BeforeSuite(alwaysRun = true)
    public static void createDriver() throws MalformedURLException {
            driver = DriverSingleton.getInstanceOfSingletonBrowserClass().getDriver();
            report = new ExtentReports(System.getProperty("user.dir") + "/src/test/java/qtriptest/tests/ExtentReport.html", true);
            test = report.startTest("QTripSearchcity");
       
    }

    @Test(description = "Search City & filters", priority = 2, groups = "2", dataProvider = "qtripUIData", dataProviderClass = DP.class)
    public void TestCase02(String cityName, String categoryFilter, String durationFilter, String expectedFilteredResults, String expectedUnfilteredResults) throws InterruptedException, IOException {
        homePage = new HomePage(driver);
        adventurePage = new AdventurePage(driver);
        loginPage = new LoginPage(driver);
        registerPage = new RegisterPage(driver);

        driver.get("https://qtripdynamic-qa-frontend.vercel.app/");
        homePage.selectOptionOnHomePage("register");
        Thread.sleep(5000);
        homePage.selectOptionOnHomePage("login");
        Thread.sleep(5000);
        adventurePage.searchCity(cityName);
        Thread.sleep(3000);
        adventurePage.DurationFilter(durationFilter);
        adventurePage.CategoryFilter(categoryFilter);
        adventurePage.verifyDataDisplayedAfterFilter(categoryFilter);
        try{
      
              adventurePage.verifyAllRecordsDisplayedAfterFilter(expectedFilteredResults, expectedUnfilteredResults);        
              test.log(LogStatus.PASS, test.addScreenCapture(capture(driver)) + "Test Pass"); 
              //   test.log(LogStatus.PASS, "All records are displayed");
            }catch(AssertionError e){
            //    test.log(LogStatus.FAIL, test.addScreenCapture(capture(driver)) + "Test Failed"); 
            test.log(LogStatus.FAIL, "All records are not displayed");
        }adventurePage.clear();
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
            // TODO - Write the test to filesystem
            report.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void quitDriver() {
              try {
            // driver.quit();
            // TODO - End the test
            report.endTest(test);
            // TODO - Write the test to filesystem
            report.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
