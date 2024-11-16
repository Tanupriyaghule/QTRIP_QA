Qtrip QA Project Documentation:
-----------------------------------------------------------------------------

This project contains automated test cases for the QTrip Dynamic web application, which is designed for users to explore and book travel adventures. The automated tests are built using Java, Selenium WebDriver, TestNG, and integrated with ExtentReports for detailed reporting.
Project Overview

QTrip Dynamic is a travel application that allows users to:

  1] Register and log in to their accounts.
  2]Search for cities and apply filters to find travel adventures.
  3] Book travel adventures with details such as guest names and dates.
  4] View and manage booking history.

This automation suite is designed to validate the critical functionalities of the application to ensure it works as expected. The test cases automate the core user journeys and scenarios for regression and acceptance testing.
----------------------------------------------------------------------------
Features of the Automation Suite

  1]Modular Page Object Model (POM):
        The project follows the POM design pattern to separate the UI locators and actions, improving readability and maintainability.

  2] Comprehensive Test Coverage:
        Test cases cover user registration, login, searching for cities and adventures, booking flows, and viewing booking history.

   3]Parallel Execution Ready:
        Thread-local WebDriver management supports parallel test execution for faster results.

  4] Integrated Reporting:
        Real-time and detailed test execution reports using ExtentReports.

   5]Reusable Components:
        Common functionalities (e.g., driver initialization, page object creation) are encapsulated in the BaseTest class.

  6] Configuration-Driven:
        Test execution can be adapted easily by configuring parameters like the base URL.
=======================================================================================================================
Test Case Details:
Test Case 01: User Registration and Login

    Description: Validates user registration and login functionality.
    Steps:
        Navigate to the registration page and create a new user account.
        Validate successful navigation to the login page.
        Log in with the newly created account credentials.
        Verify if the user is logged in successfully.
----------------------------------------------------------------------------------------------
Test Case 02: City Search and Filters

    Description: Tests the search and filtering functionality for adventures.
    Steps:
        Navigate to the city search page.
        Search for a city and validate the search results.
        Apply duration and category filters.
        Validate if the filtered results match the expected criteria.
--------------------------------------------------------------------------------------------
Test Case 03: Adventure Booking and Cancellation

    Description: Automates the booking process for adventures.
    Steps:
        Log in with a registered user account.
        Search for a specific city and select an adventure.
        Enter guest details and book the adventure.
        Verify if the booking is successful.
----------------------------------------------------------------------------------------------
Test Case 04: Booking History Management

    Description: Verifies the booking history functionality.
    Steps:
        Log in with a registered user account.
        Perform multiple bookings across different adventures.
        Navigate to the booking history page.
        Validate that all previous bookings are displayed correctly.
------------------------------------------------------------------------------------------------
Technologies and Tools Used

    Programming Language: Java
    Frameworks: TestNG, Selenium WebDriver
    Reporting Tool: ExtentReports
    Design Pattern: Page Object Model (POM)
    Build Tool: Maven
    Browser: ChromeDriver
    Test Data Management: DataProvider (TestNG)
--------------------------------------------------------------------------------------

Directory Structure

qtrip-dynamic-automation/
│
├── src/
│   ├── main/java/qtriptest/
│   │   ├── pages/           # Page Object Model classes
│   │   ├── DriverSingleton.java  # Driver management
│   ├── test/java/qtriptest/tests/
│       ├── BaseTest.java    # Test base class
│       ├── TestCase_01.java # User registration and login tests
│       ├── TestCase_02.java # City search and filters tests
│       ├── TestCase_03.java # Adventure booking tests
│       ├── TestCase_04.java # Booking history tests
│
├── pom.xml                  # Maven configuration file

---------------------------------------------------------------------------------------------------------------------------------
Conclusion

The QTrip Dynamic Automated Test Suite ensures the reliability of the application's core functionalities. It is designed to be maintainable, scalable, and efficient for both development and QA teams. Future enhancements could include:

    Integration with CI/CD pipelines (e.g., Jenkins, GitHub Actions).
    Support for cross-browser testing.
    Enhanced data-driven testing capabilities.
============================================================================================================================
Explanation: Data Provider Class (DP.java)

The DP class is responsible for reading data from an Excel file and providing it as input to the automated test cases in the QTrip Dynamic project. The use of TestNG's @DataProvider enables parameterized testing by supplying external data to test methods dynamically.
---------------------------------------------------
Key Features of DP.java

 1] Excel Data Handling with Apache POI:
        The class uses the Apache POI library to read and process data from an Excel file.
        It supports .xlsx files, making it easy to manage test data in a tabular format.

 2] Reusable Method for Reading Sheets:
        The dpMethod(String sheet) function is a generic method that takes the name of an Excel sheet as input and returns a two-dimensional array of data (Object[][]) for use in TestNG tests.

 3] Dynamic Data Providers:
        Multiple data providers (qtripData, qtripUIData, qtripBookData, qtripData1) are defined, each fetching data from a specific sheet in the Excel file.
        This structure ensures flexibility and reusability for various test scenarios.

  4]Excel File Path:
        The path to the Excel file is:

        /home/crio-user/workspace/ghuletanupriya-ME_QTRIP_QA_V2/app/src/test/resources/DatasetsforQTrip.xlsx

        Ensure the file exists and the sheet names match the ones used in the test cases.

    Flexible Data Conversion:
        Supports both STRING and NUMERIC cell types.
        Converts numeric values to strings for uniform handling.

Breakdown of Code Logic

    dpMethod(String sheet)
        Purpose: Reads data from the specified sheet in the Excel file and processes it into a String[][] format.
        Steps:
            Opens the Excel file using a FileInputStream.
            Reads the specified sheet using the Workbook object.
            Iterates through rows and cells:
                Skips the first row and column (assumed to be headers).
                Processes STRING and NUMERIC cell types.
                Collects non-empty rows into a List<List<String>>.
            Converts the list of lists into a 2D string array.

    Data Providers:
        Each method, annotated with @DataProvider, calls dpMethod with a specific sheet name.
        Example:

        @DataProvider(name = "qtripData")
        public Object[][] TestCase01() throws IOException {
            return dpMethod("TestCase01");
        }

Usage in Test Cases

The data providers are linked to test methods in the test cases using the dataProvider attribute of the @Test annotation. For example:

@Test(description = "User Register and Login", priority = 1, groups = "1", dataProvider = "qtripData", dataProviderClass = DP.class)
public static void TestCase01(String email, String password) throws TimeoutException, IOException {
    // Test logic here
}
------------------------------------------------------------------------
Execution Flow:

  1]TestNG Framework:
        TestNG identifies the dataProvider attribute and fetches the corresponding data.
 2] Dynamic Input:
        Each row of the Excel sheet becomes an input set for the test case.
        Test cases are executed iteratively for all rows in the data.
----------------------------------------------------------------------
Advantages

 1] Centralized Data Management:
        Test data is maintained in an Excel file, simplifying updates and modifications.
  2]Scalable Testing:
        Supports adding new test cases or scenarios by simply adding new sheets or rows.
  3] Data-Driven Approach:
        Allows running the same test with multiple datasets, ensuring robust validation.
---------------------------------------------------------------------------------------
Dependencies

Ensure the following dependencies are included in your pom.xml file:

<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi</artifactId>
    <version>5.2.3</version>
</dependency>
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi-ooxml</artifactId>
    <version>5.2.3</version>
</dependency>
------------------------------------------------------

This DP.java class makes it simple to scale and manage test data, ensuring that your automation suite is adaptable to evolving requirements.
