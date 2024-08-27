package test;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import page.HomePage;
import page.login.LoginPage;
import utils.run.BaseTest;
import utils.run.ProjectProperties;

@Epic("User Account Management")
public class LoggingInTest extends BaseTest {

    private static final String EMAIL_BASE = ProjectProperties.getUserName();
    private static final String PASSWORD_BASE = ProjectProperties.getPassword();

    @Severity(SeverityLevel.BLOCKER)
    @Story("US_01.001 Logging In")
    @Description("TC_01.001.01 Validate Successful Login with Correct Credentials")
    @Test
    public void testSuccessfulLoginWithCorrectCredentials() {
        String expectedHeadLine = "Firefly III What's playing?";

        String actualHeadLine = new LoginPage(getDriver())
                .setEmail(EMAIL_BASE)
                .setPassword(PASSWORD_BASE)
                .clickSignIn(new HomePage(getDriver()))
                .headline();

        Allure.step("After successful login, the user will be on the main page and will see the inscription on " +
                "the headline:" + expectedHeadLine);
        Assert.assertEquals(actualHeadLine, expectedHeadLine,
                "If FAIL: User is not logged");
    }

    @Severity(SeverityLevel.BLOCKER)
    @Story("US_01.001 Logging In")
    @Description("TC_01.001.02 Validate Error Message for Incorrect Password")
    @Test
    public void testErrorMessageIncorrectPassword() {
        String expectedMassage = "Error! These credentials do not match our records.";

        String actualMassage = new LoginPage(getDriver())
                .setEmail(EMAIL_BASE)
                .setPassword("00000000")
                .clickSignIn(new LoginPage(getDriver()))
                .getErrorMessage();

        Allure.step(String.format("An error message is displayed: %s, indicating that the login credentials are " +
                "incorrect", expectedMassage));
        Assert.assertTrue(actualMassage.contains(expectedMassage),
                "If FAIL: The error message: '" + actualMassage + "' is not displayed or wrong");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Story("US_01.001 Logging In")
    @Description("TC_01.001.03 Validate Error Message for Non-Existent Email")
    @Test
    public void testErrorMessageNonExistentEmail() {
        String expectedMassage = "Error! These credentials do not match our records.";

        String actualMassage = new LoginPage(getDriver())
                .setEmail("any@any.moc")
                .setPassword("00000000")
                .clickSignIn(new LoginPage(getDriver()))
                .getErrorMessage();

        Allure.step(String.format("An error message is displayed: '%s', indicating that the email or password is " +
                "incorrect", expectedMassage));
        Assert.assertTrue(actualMassage.contains(expectedMassage),
                "If FAIL: The error message: '" + actualMassage + "' is not displayed or wrong");
    }

    @Ignore
    @Severity(SeverityLevel.CRITICAL)
    @Story("US_01.001 Logging In")
    @Description("TC_01.001.04 Validate Error Message for Empty Email")
    @Test
    public void testErrorMessageEmptyEmail() {
        String expectedMassage = "Email is required";

        String actualMassage = new LoginPage(getDriver())
                .setEmail("")
                .setPassword(PASSWORD_BASE)
                .clickSignIn(new LoginPage(getDriver()))
                .getErrorMessage();

        Allure.step(String.format("An error message indicating '%s' should appear", expectedMassage));
        Assert.assertTrue(actualMassage.contains(expectedMassage),
                "If FAIL: The error message: '" + actualMassage + "' is not displayed or wrong");
    }

    @Ignore
    @Severity(SeverityLevel.CRITICAL)
    @Story("US_01.001 Logging In")
    @Description("TC_01.001.05 Validate Error Message for Empty Password")
    @Test
    public void testErrorMessageEmptyPassword() {
        String expectedMassage = "Password is required";

        String actualMassage = new LoginPage(getDriver())
                .setEmail(EMAIL_BASE)
                .setPassword("")
                .clickSignIn(new LoginPage(getDriver()))
                .getErrorMessage();

        Allure.step(String.format("An error message indicating '%s' should appear", expectedMassage));
        Assert.assertTrue(actualMassage.contains(expectedMassage),
                "If FAIL: The error message: '" + actualMassage + "' is not displayed or wrong");
    }

    @Ignore
    @Severity(SeverityLevel.CRITICAL)
    @Story("US_01.001 Logging In")
    @Description("TC_01.001.06 Validate Error Message for Empty Email and Password")
    @Test
    public void testValidateErrorMessageEmptyEmailAndPassword() {
        String expectedMassage = "Email and Password is required";

        String actualMassage = new LoginPage(getDriver())
                .setEmail("")
                .setPassword("")
                .clickSignIn(new LoginPage(getDriver()))
                .getErrorMessage();

        Allure.step(String.format("An error message indicating '%s' should appear", expectedMassage));
        Assert.assertTrue(actualMassage.contains(expectedMassage),
                "If FAIL: The error message: '" + actualMassage + "' is not displayed or wrong");
    }
}
