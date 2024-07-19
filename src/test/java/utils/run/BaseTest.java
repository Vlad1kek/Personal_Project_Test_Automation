package utils.run;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.log.ExceptionListener;

import java.lang.reflect.Method;

@Listeners({OrderTest.class, ExceptionListener.class,})
public abstract class BaseTest {

    private WebDriver driver;
    private WebDriverWait wait;

    protected WebDriver getDriver() {
        return driver;
    }

    private void startDriver() {
        BaseUtils.log("Browser open");

        driver = ProjectProperties.createDriver();
    }

    private void getPage() {
        BaseUtils.log("Open Web page");
        BaseUtils.get(driver);
    }

    private void loginPage() {
        BaseUtils.log("Login successful");
        FireflyUtils.login(driver);
    }

    private void firstLogin() {
        BaseUtils.log("Register successful");
        FireflyUtils.firstLogin(driver);
    }

    private void createBank() {
        BaseUtils.log("Getting started");
        FireflyUtils.createBank(driver);
    }

    private void createToken() {
        BaseUtils.log("Personal Access Token create");
        FireflyUtils.createToken(driver);
    }

    private void clearData() {
        BaseUtils.log("Clear data");
        ClearData.Token();
        ClearData.clearData();
    }

    @BeforeSuite
    protected void setUp() {
        if (ProjectProperties.isServerRun()) {
            startDriver();
            getPage();
            firstLogin();
            createBank();
            createToken();
            stopDriver();
        }
    }

    @BeforeMethod
    protected void beforeMethod(Method method) {
        BaseUtils.logf("Run %s.%s", this.getClass().getName(), method.getName());
        try {
            if (method.getAnnotation(Test.class).dependsOnMethods().length == 0) {
                clearData();
                startDriver();
                getPage();
                loginPage();
            } else {
                getPage();
            }
        } catch (Exception e) {
            stopDriver();
            throw e;
        }
    }

    private void closeDriver() {
        if (driver != null) {
            driver.quit();

            driver = null;
            wait = null;
            BaseUtils.log("Browser closed");
        }
    }

    private void stopDriver() {
        try {
            FireflyUtils.logout(driver);
        } catch (Exception ignore) {}

        closeDriver();
    }

    @AfterMethod
    protected void afterMethod(Method method, ITestResult testResult) {
        if (ProjectProperties.isServerRun() && !testResult.isSuccess()) {
            BaseUtils.captureScreenFile(driver, method.getName(), this.getClass().getName());
        }
            stopDriver();

        BaseUtils.logf("Execution time is %o sec\n\n", (testResult.getEndMillis() - testResult.getStartMillis()) / 1000);
    }
}
