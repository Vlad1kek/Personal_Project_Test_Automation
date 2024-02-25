package utils.run;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.log.ExceptionListener;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Listeners({OrderTest.class, ExceptionListener.class,})
public abstract class BaseTest {

    private WebDriver driver;
    private Map<Integer, WebDriverWait> waitMap = new HashMap<>();

    protected WebDriver getDriver() {
        return driver;
    }

    private void startDriver() {
        BaseUtils.log("Browser open");

        driver = BaseUtils.createDriver();
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

    @BeforeSuite
    void setUp() {
        try {
            startDriver();
            getPage();
            firstLogin();
            stopDriver();
        }catch (Exception e){
            closeDriver();
            throw new RuntimeException(e);
        }
    }


    @BeforeMethod
    protected void beforeMethod(Method method) {
        BaseUtils.logf("Run %s.%s", this.getClass().getName(), method.getName());
        try {
            startDriver();
            getPage();
            loginPage();
        }
        catch (Exception e){
            closeDriver();
            throw new RuntimeException(e);
        }
    }

    private void closeDriver() {
        if (driver != null) {
            driver.quit();

            waitMap = new HashMap<>();
            driver = null;
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
