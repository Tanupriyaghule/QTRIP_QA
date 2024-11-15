package qtriptest;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import com.relevantcodes.extentreports.ExtentReports;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
public class DriverSingleton {
    private static DriverSingleton instanceOfSingletonBrowserClass = null;
    private RemoteWebDriver driver;
    private static ExtentReports report;
   
    
    private DriverSingleton() throws MalformedURLException {
        final DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(BrowserType.CHROME);
        driver = new RemoteWebDriver(new URL("http://localhost:8082/wd/hub"), capabilities);
        driver.manage().window().maximize();
        report = new ExtentReports(System.getProperty("user.dir") + "/src/test/java/qtriptest/tests/ExtentReport.html", true);
        File configFile = new File(System.getProperty("user.dir") + "/src/test/java/qtriptest/tests/configExtentReport.xml");
        report.loadConfig(configFile);
    }
    public static DriverSingleton getInstanceOfSingletonBrowserClass() throws MalformedURLException {
        if (instanceOfSingletonBrowserClass == null) {
            instanceOfSingletonBrowserClass = new DriverSingleton();
        }
        return instanceOfSingletonBrowserClass;
    }
    public RemoteWebDriver getDriver() {
        return driver;
    }
    public static void closeDriverInstance() {
        if (instanceOfSingletonBrowserClass != null) {
            instanceOfSingletonBrowserClass.driver.quit();
            instanceOfSingletonBrowserClass = null;
        }
    }
    public static RemoteWebDriver getDriverInstance(String string) {
        return null;
    }
}
