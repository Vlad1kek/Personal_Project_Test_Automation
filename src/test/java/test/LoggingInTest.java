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

    private static final String EMAIL_DEFAULT = ProjectProperties.getUserName();
    private static final String PASSWORD_DEFAULT = ProjectProperties.getPassword();
    private static final String EMAIL_SECOND = "test2@gmail.com";
    private static final String PASSWORD_SECOND = "^xk!!(SCjLkhjwvu";
    private static final String PASSWORD2_SECOND = "8&Vx!V*s9!mg+8Jb";

    @Severity(SeverityLevel.BLOCKER)
    @Story("US_01.001 Logging In")
    @Description("TC_01.001.01 Validate Successful Login with Correct Credentials")
    @Test
    public void testSuccessfulLoginWithCorrectCredentials() {
        String expectedHeadLine = "Firefly III What's playing?";

        String actualHeadLine = new LoginPage(getDriver())
                .setEmail(EMAIL_DEFAULT)
                .setPassword(PASSWORD_DEFAULT)
                .clickSubmit(new HomePage(getDriver()))
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
                .setEmail(EMAIL_DEFAULT)
                .setPassword("00000000")
                .clickSubmit(new LoginPage(getDriver()))
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
                .clickSubmit(new LoginPage(getDriver()))
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
                .setPassword(PASSWORD_DEFAULT)
                .clickSubmit(new LoginPage(getDriver()))
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
                .setEmail(EMAIL_DEFAULT)
                .setPassword("")
                .clickSubmit(new LoginPage(getDriver()))
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
                .clickSubmit(new LoginPage(getDriver()))
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
            loginPage.setEmail(EMAIL_DEFAULT);
            loginPage.setPassword("123");
            loginPage.clickSubmit(loginPage);
            String actualMessage = loginPage.getErrorMessage();

            if (actualMessage.contains(expectedMessage)) {
                // Failed attempt, continue the cycle
                LogUtils.logInfo("Attempt #" + attempt + ": Failed");
            }
        }

        loginPage.setEmail(EMAIL_DEFAULT);
        loginPage.setPassword(PASSWORD_DEFAULT);
        loginPage.clickSubmit(loginPage);
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
                .setEmail(EMAIL_DEFAULT)
                .setPassword(PASSWORD_DEFAULT)
                .setRememberMe()
                .clickSubmit(new HomePage(getDriver()))
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
                .enterText(EMAIL_DEFAULT)
                .pressTab()
                .enterText(PASSWORD_DEFAULT)
                .pressTab()
                .pressTab()
                .pressEnter()
                .getHeadline();

        Allure.step("The user should be to log into the application");
        Assert.assertEquals(homePage, "Firefly III What's playing?",
                "If FAIL: User is not logged in");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Story("US_01.001 Logging In")
    @Description("TC_01.001.12 Validate Placeholder Email Text")
    @Test
    public void testPlaceholderEmailText() {
        String expectedEmailPlaceholder = "Email address";

        String actualEmailPlaceholder = new LoginPage(getDriver())
                .getEmailPlaceholder();

        Allure.step(String.format("Check the Email placeholder text: '%s' inside the text boxes", expectedEmailPlaceholder));
        Assert.assertEquals(actualEmailPlaceholder, expectedEmailPlaceholder,
                "If FAIL: Incorrect Email placeholder text displayed inside as: " + actualEmailPlaceholder);
    }

    @Severity(SeverityLevel.CRITICAL)
    @Story("US_01.001 Logging In")
    @Description("TC_01.001.13 Validate Placeholder Password Text")
    @Test
    public void testPlaceholderPasswordText() {
        String expectedPasswordPlaceholder = "Password";

        String actualPasswordPlaceholder = new LoginPage(getDriver())
                .getPasswordPlaceholder();

        Allure.step(String.format("Check the Password placeholder text: '%s' inside the text boxes", expectedPasswordPlaceholder));
        Assert.assertEquals(actualPasswordPlaceholder, expectedPasswordPlaceholder,
                "If FAIL: Incorrect Password placeholder text displayed inside as: " + actualPasswordPlaceholder);
    }

    @Severity(SeverityLevel.CRITICAL)
    @Story("US_01.001 Logging In")
    @Description("TC_01.001.14 Validate Browser Back Button in Login")
    @Test
    public void testBrowserBackButtonInLogin() {
        String homePage = new LoginPage(getDriver())
                .setEmail(EMAIL_DEFAULT)
                .setPassword(PASSWORD_DEFAULT)
                .clickSubmit(new HomePage(getDriver()))
                .clickBackBrowserButton(new HomePage(getDriver()))
                .refreshPage(new HomePage(getDriver()))
                .getHeadline();

        Allure.step("The user is should not logged out, he will remain on the main page");
        Assert.assertEquals(homePage, "Firefly III What's playing?",
                "If FAIL: The user has logged out");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Story("US_01.001 Logging In")
    @Description("TC_01.001.15 Validate Browser Back Button in Logout")
    @Test
    public void testBrowserBackButtonInLogout() {
        String homePage = new LoginPage(getDriver())
                .setEmail(EMAIL_DEFAULT)
                .setPassword(PASSWORD_DEFAULT)
                .clickSubmit(new HomePage(getDriver()))
                .clickLogout()
                .clickBackBrowserButton(new HomePage(getDriver()))
                .refreshPage(new HomePage(getDriver()))
                .getLoginBoxMsg();

        Allure.step("User should not get logged in again, he will be returned to the login page");
        Assert.assertEquals(homePage, "Sign in to start your session",
                "If FAIL: The user has no logged out");
    }

    @Ignore
    @Severity(SeverityLevel.CRITICAL)
    @Story("US_01.001 Logging In")
    @Description("TC_01.001.22 Validate Secure Connection (HTTPS)")
    @Test
    public void testSecureConnectionHTTPS() {
        String loginPageURL = new LoginPage(getDriver())
                .getUrlAddress();

        Assert.assertTrue(loginPageURL.startsWith("https://"),
                "If FAIL: Connection is not secure");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Story("US_01.001 Logging In")
    @Description("TC_01.001.17 Validate No Logging Old Password After Changing Password")
    @Test()
    public void testNoLoggingAfterChangingPassword() {
        String homePage = new LoginPage(getDriver())
                .setEmail(EMAIL_SECOND)
                .setPassword(PASSWORD_SECOND)
                .clickSubmit(new HomePage(getDriver()))
                .clickOption()
                .clickProfile()
                .clickChangePassword()
                .setCurrentPassword(PASSWORD_SECOND)
                .setNewPassword(PASSWORD2_SECOND)
                .setNewPasswordAgain(PASSWORD2_SECOND)
                .clickChangeYourPassword()
                .clickLogout()
                .setEmail(EMAIL_SECOND)
                .setPassword(PASSWORD_SECOND)
                .clickSubmit(new LoginPage(getDriver()))
                .getErrorMessage();

        Allure.step("User should not be allowed to login");
        Assert.assertTrue(homePage.contains("Error! These credentials do not match our records."),
                "If FAIL: The user has logged into the system");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Story("US_01.001 Logging In")
    @Description("TC_01.001.18 Validate Logging New Password After Changing Password")
    @Test(dependsOnMethods = "testNoLoggingAfterChangingPassword")
    public void testLoggingNewPasswordAfterChangingPassword() {
        String homePage = new LoginPage(getDriver())
                .setEmail(EMAIL_SECOND)
                .setPassword(PASSWORD2_SECOND)
                .clickSubmit(new HomePage(getDriver()))
                .clickOption()
                .clickProfile()
                .clickChangePassword()
                .setCurrentPassword(PASSWORD2_SECOND)
                .setNewPassword(PASSWORD_SECOND)
                .setNewPasswordAgain(PASSWORD_SECOND)
                .clickChangeYourPassword()
                .clickLogout()
                .setEmail(EMAIL_SECOND)
                .setPassword(PASSWORD_SECOND)
                .clickSubmit(new HomePage(getDriver()))
                .getHeadline();

        Allure.step("User should be able to login");
        Assert.assertEquals(homePage, "Welcome to Firefly III!",
                "If FAIL: The user has NO logged into the system");
    }
}