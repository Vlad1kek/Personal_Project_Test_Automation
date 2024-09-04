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

@Listeners(ExceptionListener.class)
public abstract class BaseTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private final Faker faker = new Faker();

    protected WebDriver getDriver() {
        return driver;
    }

    protected Faker getFaker() {
        return faker;
    }

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

    private void initializeSetup() {
        startDriver();
        getPage();
        firstLogin();
        createBank();
        createToken();
        setUserCreate();
        createSecondUser();
    }

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

    private boolean isLoggingInTest() {
        return getClass().getName().contains("LoggingIn");
    }

    private boolean isRegisterAccountTest() {
        return getClass().getName().contains("RegisterAccount");
    }

    @AfterMethod
    protected void afterMethod(Method method, ITestResult result) {
        if (ProjectProperties.isServerRun() && !result.isSuccess()) {
            captureFailureDetails(method);
        }

        stopDriver();
        if (result.isSuccess()) {
            LogUtils.logSuccess("Test was success");
        }

        LogUtils.logf("Execution time is %.3f sec\n\n", (result.getEndMillis() - result.getStartMillis()) / 1000.0);
    }

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

    private void startDriver() {
        LogUtils.logInfo("Launching browser");
        driver = ProjectProperties.createDriver();
    }

    private void getPage() {
        LogUtils.logInfo("Navigating to web page");
        BaseUtils.getUrl(driver);
    }

    private void loginToAccount() {
        LogUtils.logInfo("Login to account");
        FireflyUtils.login(driver);
    }

    private void firstLogin() {
        LogUtils.logInfo("Start of user registration");
        FireflyUtils.firstLogin(driver);
    }

    private void moveToRegisterPage() {
        LogUtils.logInfo("Opening registration page");
        FireflyUtils.moveToRegisterAccount(driver);
    }

    private void createBank() {
        LogUtils.logInfo("Creating 'Personal Bank' account");
        FireflyUtils.createBank(driver, wait);
    }

    private void createToken() {
        LogUtils.logInfo("Generating personal access token");
        FireflyUtils.createToken(driver, getWait());
    }

    private void clearData() {
        LogUtils.logInfo("Clearing test data");
        ClearData.getToken();
        ClearData.clearData();
    }

    private void setUserCreate() {
        LogUtils.logInfo("Enabling user registration feature");
        FireflyUtils.enableUserRegistration(driver);
    }

    private void createSecondUser() {
        LogUtils.logInfo("Creating second user account");
        FireflyUtils.createNewUser(driver);
    }

    private void closeDriver() {
        if (driver != null) {
            LogUtils.logInfo("Closing browser");
            driver.quit();
            driver = null;
            wait = null;
        }
    }

    protected WebDriverWait getWait() {
        if (wait == null) {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(2));
        }
        return wait;
    }

    private void stopDriver() {
        try {
            FireflyUtils.logout(driver);
        } catch (Exception ignore) {}
        closeDriver();
    }
}
