package utils.run;

import com.github.javafaker.Faker;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.log.ExceptionListener;
import utils.log.LogUtils;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Method;
import java.time.Duration;

/**
 * Abstract base class for Selenium WebDriver test automation.
 * <p>
 * The `BaseTest` class handles:
 * <ul>
 *     <li>Managing the WebDriver instance for browser automation.</li>
 *     <li>Setting up and tearing down test environments.</li>
 *     <li>Logging test execution information and results using Log4j2.</li>
 *     <li>Clearing test data before each test run.</li>
 *     <li>Capturing screenshots on test failures for better debugging.</li>
 * </ul>
 *
 * <p><strong>Usage Examples:</strong></p>
 * <ul>
 *     <li>To start a WebDriver session: <code>startDriver();</code></li>
 *     <li>To clear test data: <code>clearData();</code></li>
 * </ul>
 */
@Listeners(ExceptionListener.class)
public abstract class BaseTest {

    /**
     * The WebDriver instance used for browser automation.
     */
    private WebDriver driver;

    /**
     * WebDriverWait instance for managing explicit waits in tests.
     */
    private WebDriverWait wait;

    /**
     * Faker instance for generating random test data (e.g., names, emails).
     */
    private final Faker faker = new Faker();

    /**
     * Retrieves the current WebDriver instance.
     *
     * @return the WebDriver instance used for browser interactions.
     */
    protected WebDriver getDriver() {
        return driver;
    }

    /**
     * Retrieves the Faker instance for generating random test data.
     *
     * @return the Faker instance.
     */
    protected Faker getFaker() {
        return faker;
    }

    /**
     * Sets up the test environment before the test suite execution.
     * <p>
     * If running on a server, this method initializes the WebDriver and performs
     * initial login and data setup.
     * </p>
     */
    @BeforeSuite
    protected void setUp() {
        try {
            if (ProjectProperties.isServerRun()) {
                initializeSetup();
            }
        } catch (Exception e) {
            LogUtils.logFatal(e.getMessage());
            throw e;
        } finally {
            stopDriver();
        }
    }

    /**
     * Initializes the test setup, which includes starting the browser, navigating to the application,
     * and performing the initial login and setup tasks.
     */
    private void initializeSetup() {
        startDriver();
        getPage();
        firstLogin();
        createBank();
        createToken();
        setUserCreate();
        createSecondUser();
    }

    /**
     * Method executed before each test method.
     * <p>
     * It clears test data, starts a new WebDriver session, and navigates to the appropriate page based on the test.
     * </p>
     *
     * @param method the test method being executed.
     */
    @BeforeMethod
    protected void beforeMethod(Method method) {
        LogUtils.logf("Run %s.%s", this.getClass().getName(), method.getName());
        try {
            if (method.getAnnotation(Test.class).dependsOnMethods().length == 0) {
                clearData();
            }

            startDriver();
            getPage();

            if (isRegisterAccountTest()) {
                moveToRegisterPage();
            } else if (!isLoggingInTest()){
                loginToAccount();
            }

            LogUtils.logInfo("Starting test execution");
        } catch (Exception e) {
            closeDriver();
            throw e;
        }
    }

    /**
     * Checks whether the current test is related to logging in.
     *
     * @return true if the current test involves logging in, false otherwise.
     */
    private boolean isLoggingInTest() {
        return getClass().getName().contains("LoggingIn");
    }

    /**
     * Checks whether the current test is related to account registration.
     *
     * @return true if the current test involves registering an account, false otherwise.
     */
    private boolean isRegisterAccountTest() {
        return getClass().getName().contains("RegisterAccount");
    }

    /**
     * Method executed after each test method.
     * <p>
     * It captures failure details (such as screenshots) if the test fails, and closes the WebDriver instance.
     * </p>
     *
     * @param method the test method that was executed.
     * @param result the result of the test execution.
     */
    @AfterMethod
    protected void afterMethod(Method method, ITestResult result) {
        if (ProjectProperties.isServerRun() && !result.isSuccess()) {
            captureFailureDetails(method);
        }

        closeDriver();
        if (result.isSuccess()) {
            LogUtils.logSuccess("Test was success");
        }

        LogUtils.logf("Execution time is %.3f sec\n\n", (result.getEndMillis() - result.getStartMillis()) / 1000.0);
    }

    /**
     * Captures a screenshot if a test fails, and attaches it to the Allure report.
     *
     * @param method the test method that failed.
     */
    private void captureFailureDetails(Method method) {
        try {
            BaseUtils.captureScreenFile(driver, method.getName(), this.getClass().getName());

            Allure.addAttachment(
                    "screenshot.png",
                    "image/png",
                    new ByteArrayInputStream(((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES)),
                    "png");
        } catch (Exception e) {
            LogUtils.logError("Failed to capture screenshot: " + e.getMessage());
        }
    }

    /**
     * Starts the WebDriver session by launching the browser.
     */
    private void startDriver() {
        LogUtils.logInfo("Launching browser");
        driver = ProjectProperties.createDriver();
    }

    /**
     * Navigates to the application URL as defined in the project properties.
     */
    private void getPage() {
        LogUtils.logInfo("Navigating to web page");
        BaseUtils.getUrl(driver);
    }

    /**
     * Logs into the application using predefined credentials.
     */
    private void loginToAccount() {
        LogUtils.logInfo("Login to account");
        FireflyUtils.login(driver);
    }

    /**
     * Handles the initial login and account setup.
     */
    private void firstLogin() {
        LogUtils.logInfo("Start of user registration");
        FireflyUtils.firstLogin(driver);
    }

    /**
     * Navigates to the registration page for account creation.
     */
    private void moveToRegisterPage() {
        LogUtils.logInfo("Opening registration page");
        FireflyUtils.moveToRegisterAccount(driver);
    }

    /**
     * Creates a personal bank account for testing purposes.
     */
    private void createBank() {
        LogUtils.logInfo("Creating 'Personal Bank' account");
        FireflyUtils.createBank(driver);
    }

    /**
     * Generates a personal access token for API access.
     */
    private void createToken() {
        LogUtils.logInfo("Generating personal access token");
        FireflyUtils.createToken(driver, getWait());
    }

    /**
     * Clears any existing test data before executing the next test.
     */
    private void clearData() {
        LogUtils.logInfo("Clearing test data");
        ClearData.getToken();
        ClearData.clearData();
    }

    /**
     * Enables the user registration feature in the application.
     */
    private void setUserCreate() {
        LogUtils.logInfo("Enabling user registration feature");
        FireflyUtils.enableUserRegistration(driver);
    }

    /**
     * Creates a second user account for testing multi-user scenarios.
     */
    private void createSecondUser() {
        LogUtils.logInfo("Creating second user account");
        FireflyUtils.createNewUser(driver);
    }

    /**
     * Closes the WebDriver session by quitting the browser and releasing resources.
     */
    private void closeDriver() {
        if (driver != null) {
            LogUtils.logInfo("Closing browser");
            driver.quit();
            driver = null;
            wait = null;
        }
    }

    /**
     * Retrieves the WebDriverWait instance, initializing it if necessary.
     *
     * @return the WebDriverWait instance for explicit waits.
     */
    protected WebDriverWait getWait() {
        if (wait == null) {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(2));
        }
        return wait;
    }

    /**
     * Safely stops the WebDriver session by attempting to log out and closing the browser.
     */
    private void stopDriver() {
        try {
            FireflyUtils.logout(driver);
        } catch (Exception ignore) {}
        closeDriver();
    }
}
