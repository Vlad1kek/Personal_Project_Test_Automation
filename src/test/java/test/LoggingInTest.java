package test;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import page.HomePage;
import page.login.LoginPage;
import utils.log.LogUtils;
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
                .getHeadline();

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
    public void testErrorMessageEmptyEmailAndPassword() {
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

    @Ignore
    @Severity(SeverityLevel.CRITICAL)
    @Story("US_01.001 Logging In")
    @Description("TC_01.001.07 Validate Account Lockout After Multiple Failed Attempts")
    @Test
    public void testAccountLockoutAfterMultipleFailedAttempts() {
        String expectedMessage = "Error! These credentials do not match our records.";
        String expectedError = "Error! Your account it temporarily locked due to multiple failed logins.";
        int maxAttempts = 5;

        LoginPage loginPage = new LoginPage(getDriver());

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            loginPage.setEmail(EMAIL_BASE);
            loginPage.setPassword("123");
            loginPage.clickSignIn(loginPage);
            String actualMessage = loginPage.getErrorMessage();

            if (actualMessage.contains(expectedMessage)) {
                // Неудачная попытка, продолжаем цикл
                LogUtils.logInfo("Attempt #" + attempt + ": Failed");
            } else {
                // Успешная попытка, завершаем цикл
                LogUtils.logInfo("Attempt #" + attempt + ": Succeeded");
                break;
            }
        }
        loginPage.setEmail(EMAIL_BASE);
        loginPage.setPassword(PASSWORD_BASE);
        loginPage.clickSignIn(loginPage);
        String actualError = loginPage.getErrorMessage();

        Allure.step(String.format("The account should be temporarily locked, and an appropriate message should be " +
                "displayed: '%s', indicating that the account is locked due to multiple failed login attempts", expectedError));
        Assert.assertTrue(expectedError.contains(actualError),
                "If FAIL: The error message: '" + actualError + "' is not displayed or wrong");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Story("US_01.001 Logging In")
    @Description("TC_01.001.08 Validate Password Toggle Visibility")
    @Test
    public void testPasswordToggleVisibility() {
        String passwordFieldType = new LoginPage(getDriver())
                .setPassword("qwert")
                .getPasswordFieldType();

        Allure.step("The password should be masked (e.g., displayed as dots) to prevent others from seeing it.");
        Assert.assertEquals(passwordFieldType, "password",
                "If FAIL: The password field is not masked");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Story("US_01.001 Logging In")
    @Description("TC_01.001.09 Validate 'Remember Me' Option")
    @Test
    public void testRememberMeOption() {
        String homePage = new LoginPage(getDriver())
                .setEmail(EMAIL_BASE)
                .setPassword(PASSWORD_BASE)
                .setRememberMe()
                .clickSignIn(new HomePage(getDriver()))
                .closeDriverAndOpenURL()
                .getHeadline();

        Allure.step("The user should remain logged in and not be prompted to enter credentials again on the " +
                "same device");
        Assert.assertEquals(homePage, "Firefly III What's playing?",
                "If FAIL: User is not logged in");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Story("US_01.001 Logging In")
    @Description("TC_01.001.10 Validate 'Forgot Password?' Functionality")
    @Test
    public void testForgotPasswordFunctionality() {
        String forgotPasswordPage = new LoginPage(getDriver())
                .clickForgotPassword()
                .getHeadLine();

        Allure.step("The user should be taken to 'Forgotten Password' page");
        Assert.assertEquals(forgotPasswordPage, "Reset your password",
                "If FAIL: The user was not redirected to the 'Forgot your password?' page.");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Story("US_01.001 Logging In")
    @Description("TC_01.001.11 Validate Tab and Enter Keyboard Keys")
    @Test
    public void testTabAndEnterKeyboardKeys() {
        String homePage = new LoginPage(getDriver())
                .loggingIntoUsingKeyboard(EMAIL_BASE, PASSWORD_BASE)
                .getHeadline();

        Allure.step("The user should be to log into the application");
        Assert.assertEquals(homePage, "Firefly III What's playing?",
                "If FAIL: User is not logged in");
    }
}