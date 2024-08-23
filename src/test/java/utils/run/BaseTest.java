package utils.run;

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

@Listeners({OrderTest.class, ExceptionListener.class,})
public abstract class BaseTest {
    private WebDriver driver;
    private WebDriverWait wait;

    protected WebDriver getDriver() {
        return driver;
    }

    private void startDriver() {
        LogUtils.logInfo("Open Browser");
        driver = ProjectProperties.createDriver();
    }

    private void getPage() {
        LogUtils.logInfo("Open Web page");
        BaseUtils.getUrl(driver);
    }

    private void loginPage() {
        LogUtils.logInfo("Login successful");
        FireflyUtils.login(driver);
    }

    private void firstLogin() {
        LogUtils.logInfo("Register successful");
        FireflyUtils.firstLogin(driver);
    }

    private void createBank() {
        LogUtils.logInfo("Personal Bank Create");
        FireflyUtils.createBank(driver);
    }

    private void createToken() {
        LogUtils.logInfo("Personal Access Token create");
        FireflyUtils.createToken(driver, getWait());
    }

    private void clearData() {
        LogUtils.logInfo("Clear data");
        ClearData.getToken();
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
        LogUtils.logf("Run %s.%s", this.getClass().getName(), method.getName());
        try {
            if (method.getAnnotation(Test.class).dependsOnMethods().length == 0) {
                clearData();
                startDriver();
                getPage();
                loginPage();
                LogUtils.logInfo("Start run test");
            } else {
                getPage();
            }
        } catch (Exception e) {
            closeDriver();
            throw e;
        }
    }

    private void closeDriver() {
        if (driver != null) {
            driver.quit();

            driver = null;
            wait = null;
            LogUtils.logInfo("Browser closed");
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

    @AfterMethod
    protected void afterMethod(Method method, ITestResult testResult) {
        if (ProjectProperties.isServerRun() && !testResult.isSuccess()) {
            BaseUtils.captureScreenFile(driver, method.getName(), this.getClass().getName());

            Allure.addAttachment(
                    "screenshot.png",
                    "image/png",
                    new ByteArrayInputStream(((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES)),
                    "png");
        }

        if (testResult.isSuccess()) {
            LogUtils.logSuccess("Test was success");
        }

        if (method.getAnnotation(Test.class).dependsOnMethods().length != 0 && !(!ProjectProperties.isServerRun() && !testResult.isSuccess())) {
            stopDriver();
        }

        LogUtils.logf("Execution time is %.3f sec\n\n", (testResult.getEndMillis() - testResult.getStartMillis()) / 1000.0);
    }
}
