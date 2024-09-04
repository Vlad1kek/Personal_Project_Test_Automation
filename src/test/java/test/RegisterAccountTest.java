package test;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import page.HomePage;
import page.login.RegisterAccountPage;
import utils.run.BaseTest;
import utils.run.ProjectProperties;

@Epic("Register an Account")
public class RegisterAccountTest extends BaseTest {
    private static final String EMAIL_DEFAULT = ProjectProperties.getUserName();
    private static final String PASSWORD_DEFAULT = ProjectProperties.getPassword();

    @Severity(SeverityLevel.CRITICAL)
    @Story("US_01.001 Basic Account Registration")
    @Description("TC_01.001.01 Validate Successful Account Registration")
    @Test
    public void testSuccessfulAccountRegistration() {
        String homePage = new RegisterAccountPage(getDriver())
                .setEmail(getFaker().internet().safeEmailAddress())
                .setPassword(PASSWORD_DEFAULT)
                .setPasswordConfirm(PASSWORD_DEFAULT)
                .clickSubmit(new HomePage(getDriver()))
                .getHeadline();

        Allure.step("The registration is successful, and the user will be redirected to the home page");
        Assert.assertTrue(homePage.contains("Welcome to Firefly III!"),
                "If FAIL: Registration Failed, or user is Not on the home page");
    }

    @Severity(SeverityLevel.BLOCKER)
    @Story("US_01.001 Basic Account Registration")
    @Description("TC_01.001.02 Validate Registration with Missing Email")
    @Test
    public void testRegistrationWithMissingEmail() {
        String exceptedError = """
                Error! There were some problems with your input.

                The email address field is required.""";

        String actualError = new RegisterAccountPage(getDriver())
                .setPassword(PASSWORD_DEFAULT)
                .setPasswordConfirm(PASSWORD_DEFAULT)
                .clickSubmit(new RegisterAccountPage(getDriver()))
                .getErrorMessage();

        Allure.step(String.format("The registration fails, and an error message is displayed '%s' indicating that " +
                "the email field is required", exceptedError));
        Assert.assertEquals(actualError, exceptedError,
                "If FAIL: Registration successful or error text: '" + actualError + "' is Incorrect");
    }

    @Severity(SeverityLevel.BLOCKER)
    @Story("US_01.001 Basic Account Registration")
    @Description("TC_01.001.04 Validate Registration with Missing Password")
    @Test
    public void testRegistrationWithMissingPassword() {
        String exceptionError = """
                Error! There were some problems with your input.

                The password field is required.""";

        String actualError = new RegisterAccountPage(getDriver())
                .setEmail(getFaker().internet().safeEmailAddress())
                .setPasswordConfirm(PASSWORD_DEFAULT)
                .clickSubmit(new RegisterAccountPage(getDriver()))
                .getErrorMessage();

        Allure.step(String.format("The registration fails, and an error message is displayed '%s' indicating that " +
                "the password field is required", exceptionError));
        Assert.assertEquals(actualError, exceptionError,
                "If FAIL: Registration successful or error text: '" + actualError + "' is Incorrect");
    }

    @Severity(SeverityLevel.BLOCKER)
    @Story("US_01.001 Basic Account Registration")
    @Description("TC_01.001.05 Validate Registration with Missing Password (Again)")
    @Test
    public void testRegistrationWithMissingPasswordAgain() {
        String exceptionError = """
                Error! There were some problems with your input.
                                               
                The password confirmation does not match.""";

        String actualError = new RegisterAccountPage(getDriver())
                .setEmail(getFaker().internet().safeEmailAddress())
                .setPassword(PASSWORD_DEFAULT)
                .clickSubmit(new RegisterAccountPage(getDriver()))
                .getErrorMessage();

        Allure.step(String.format("The registration fails, and an error message is displayed '%s' indicating that " +
                "the password confirmation field is required", exceptionError));
        Assert.assertEquals(actualError, exceptionError,
                "If FAIL: Registration successful or error text: '" + actualError + "' is Incorrect");
    }

    @Severity(SeverityLevel.MINOR)
    @Story("US_01.001 Basic Account Registration")
    @Description("TC_01.001.06 Validate Registering Keyboard Keys Use")
    @Test
    public void testRegisteringKeyboardKeysUse() {
        String homePage = new RegisterAccountPage(getDriver())
                .pressTab()
                .enterText(getFaker().internet().safeEmailAddress())
                .pressTab()
                .enterText(PASSWORD_DEFAULT)
                .pressTab()
                .enterText(PASSWORD_DEFAULT)
                .pressTab()
                .pressTab()
                .pressTab()
                .pressEnter()
                .getHeadline();

        Allure.step("The registration is successful, and the user will be redirected to the home page");
        Assert.assertEquals(homePage, "Welcome to Firefly III!",
                "If FAIL: Registration Failed, or user is Not on the home page");
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Story("US_01.001 Basic Account Registration")
    @Description("TC_01.001.07 Validate Placeholder Email Text")
    @Test
    public void testPlaceholderEmailText() {
        String expectedEmailPlaceholder = "Email address";

        String actualEmailPlaceholder = new RegisterAccountPage(getDriver())
                .getEmailPlaceholder();

        Allure.step("Proper email placeholder text displayed as: " + expectedEmailPlaceholder);
        Assert.assertEquals(actualEmailPlaceholder, expectedEmailPlaceholder,
                "If FAIL: Incorrect Email placeholder text displayed as: " + actualEmailPlaceholder);
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Story("US_01.001 Basic Account Registration")
    @Description("TC_01.001.08 Validate Placeholder Password Text")
    @Test
    public void testPlaceholderPasswordText() {
        String expectedPasswordPlaceholder = "Password";

        String actualPasswordPlaceholder = new RegisterAccountPage(getDriver())
                .getPasswordPlaceholder();

        Allure.step("Proper password placeholder text displayed as: " + expectedPasswordPlaceholder);
        Assert.assertEquals(actualPasswordPlaceholder, expectedPasswordPlaceholder,
                "If FAIL: Incorrect password placeholder text displayed as: " + actualPasswordPlaceholder);
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Story("US_01.001 Basic Account Registration")
    @Description("TC_01.001.09 Validate Placeholder Password Confirm Text")
    @Test
    public void testPlaceholderPasswordConfirmText() {
        String expectedPasswordConfirmPlaceholder = "Password (again)";

        String actualPasswordConfirmPlaceholder = new RegisterAccountPage(getDriver())
                .getPasswordConfirmPlaceholder();

        Allure.step("Proper password confirm placeholder text displayed as: " + expectedPasswordConfirmPlaceholder);
        Assert.assertEquals(actualPasswordConfirmPlaceholder, expectedPasswordConfirmPlaceholder,
                "If FAIL: Incorrect password confirm placeholder text displayed as: " + actualPasswordConfirmPlaceholder);
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("US_01.001 Basic Account Registration")
    @Description("TC_01.001.11 Validate Accepting Spaces in Input Fields")
    @Test
    public void testAcceptingSpacesInputFields() {
        String exceptedError = """
                Error! There were some problems with your input.
                
                The email address field is required.
                The password field is required.""";

        String actualError = new RegisterAccountPage(getDriver())
                .setEmail(" ")
                .setPassword(" ")
                .setPasswordConfirm(" ")
                .clickSubmit(new RegisterAccountPage(getDriver()))
                .getErrorMessage();

        Allure.step(String.format("The registration fails, and an error message is displayed '%s' indicating that " +
                "the email and password field is required", exceptedError));
        Assert.assertEquals(actualError, exceptedError,
                "If FAIL: Registration successful or error text: '" + actualError + "' is Incorrect");
    }
}
