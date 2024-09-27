package test;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import page.HomePage;
import page.login.RegisterAccountPage;
import utils.run.BaseTest;
import utils.run.ProjectProperties;

@Epic("Register an Account")
public class RegisterAccountTest extends BaseTest {
    private static final String EMAIL_DEFAULT = ProjectProperties.getPropAdminEmail();
    private static final String PASSWORD_DEFAULT = ProjectProperties.getPropPassword();

    @Severity(SeverityLevel.CRITICAL)
    @Story("US_01.001 Successful Account Registration")
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
    @Story("US_01.002 Error Handling for Registration Fields")
    @Description("TC_01.002.01 Validate Registration with Missing Email")
    @Test
    public void testRegistrationWithMissingEmail() {
        final String exceptedError = """
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
    @Story("US_01.002 Error Handling for Registration Fields")
    @Description("TC_01.002.03 Validate Registration with Missing Password")
    @Test
    public void testRegistrationWithMissingPassword() {
        final String exceptionError = """
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
    @Story("US_01.002 Error Handling for Registration Fields")
    @Description("TC_01.002.04 Validate Registration with Missing Password (Again)")
    @Test
    public void testRegistrationWithMissingPasswordAgain() {
        final String exceptionError = """
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
    @Story("US_01.006 Keyboard Accessibility in Registration")
    @Description("TC_01.006.01 Validate Registering Keyboard Keys Use")
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
    @Story("US_01.005 Password Security Features")
    @Description("TC_01.005.01 Validate Placeholder Email Text")
    @Test
    public void testPlaceholderEmailText() {
        final String expectedEmailPlaceholder = "Email address";

        String actualEmailPlaceholder = new RegisterAccountPage(getDriver())
                .getEmailPlaceholder();

        Allure.step("Proper email placeholder text displayed as: " + expectedEmailPlaceholder);
        Assert.assertEquals(actualEmailPlaceholder, expectedEmailPlaceholder,
                "If FAIL: Incorrect Email placeholder text displayed as: " + actualEmailPlaceholder);
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Story("US_01.005 Password Security Features")
    @Description("TC_01.005.02 Validate Placeholder Password Text")
    @Test
    public void testPlaceholderPasswordText() {
        final String expectedPasswordPlaceholder = "Password";

        String actualPasswordPlaceholder = new RegisterAccountPage(getDriver())
                .getPasswordPlaceholder();

        Allure.step("Proper password placeholder text displayed as: " + expectedPasswordPlaceholder);
        Assert.assertEquals(actualPasswordPlaceholder, expectedPasswordPlaceholder,
                "If FAIL: Incorrect password placeholder text displayed as: " + actualPasswordPlaceholder);
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Story("US_01.005 Password Security Features")
    @Description("TC_01.005.03 Validate Placeholder Password Confirm Text")
    @Test
    public void testPlaceholderPasswordConfirmText() {
        final String expectedPasswordConfirmPlaceholder = "Password (again)";

        String actualPasswordConfirmPlaceholder = new RegisterAccountPage(getDriver())
                .getPasswordConfirmPlaceholder();

        Allure.step("Proper password confirm placeholder text displayed as: " + expectedPasswordConfirmPlaceholder);
        Assert.assertEquals(actualPasswordConfirmPlaceholder, expectedPasswordConfirmPlaceholder,
                "If FAIL: Incorrect password confirm placeholder text displayed as: " + actualPasswordConfirmPlaceholder);
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("US_01.007 Handling Special Cases in Registration Fields")
    @Description("TC_01.007.01 Validate Accepting Spaces in Input Fields")
    @Test
    public void testAcceptingSpacesInputFields() {
        final String exceptedError = """
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

    @Severity(SeverityLevel.CRITICAL)
    @Story("US_01.003 Password Requirements")
    @Description("TC_01.003.01 Validate Password Too Short")
    @Test
    public void testPasswordTooShort() {
        final String shortPassword = "^X9zDL(h2J)9yZ&";
        final String exceptedError = """
                Error! There were some problems with your input.

                The password must be at least 16 characters.""";

        String actualError = new RegisterAccountPage(getDriver())
                .setEmail(getFaker().internet().safeEmailAddress())
                .setPassword(shortPassword)
                .setPasswordConfirm(shortPassword)
                .clickSubmit(new RegisterAccountPage(getDriver()))
                .getErrorMessage();

        Allure.step(String.format("The registration fails, and an error message is displayed '%s' indicating that " +
                "the password is too short", exceptedError));
        Assert.assertEquals(actualError, exceptedError,
                "If FAIL: Registration successful or error text: '" + actualError + "' is Incorrect");
    }

    @Ignore
    @Severity(SeverityLevel.CRITICAL)
    @Story("US_01.003 Password Requirements")
    @Description("TC_01.003.02 Validate Password Missing Special Character")
    @Test
    public void testPasswordMissingSpecialCharacter() {
        final String passWithoutSpecialChar = "566UB4RQJ7vSeVdx";
        final String exceptedError = """
                Error! There were some problems with your input.
                
                Password must contain at least one special character.""";

        String actualError = new RegisterAccountPage(getDriver())
                .setEmail(getFaker().internet().safeEmailAddress())
                .setPassword(passWithoutSpecialChar)
                .setPasswordConfirm(passWithoutSpecialChar)
                .clickSubmit(new RegisterAccountPage(getDriver()))
                .getErrorMessage();

        Allure.step(String.format("The registration fails, and an error message is displayed '%s' indicating that " +
                "the password must include a special character", exceptedError));
        Assert.assertEquals(actualError, exceptedError,
                "If FAIL: Registration successful or error text: '" + actualError + "' is Incorrect");
    }

    @Ignore
    @Severity(SeverityLevel.CRITICAL)
    @Story("US_01.003 Password Requirements")
    @Description("TC_01.003.03 Validate Password Missing Number")
    @Test
    public void testPasswordMissingNumber() {
        final String passWithoutNumber = "EfaP&qzLyx)ewkf&";
        final String exceptedError = """
                Error! There were some problems with your input.
                                
                Password must contain at least one number.""";

        String actualError = new RegisterAccountPage(getDriver())
                .setEmail(getFaker().internet().safeEmailAddress())
                .setPassword(passWithoutNumber)
                .setPasswordConfirm(passWithoutNumber)
                .clickSubmit(new RegisterAccountPage(getDriver()))
                .getErrorMessage();

        Allure.step(String.format("The registration is not submitted, an error message is displayed '%s' indicating " +
                "that the password must include at least one number", exceptedError));
        Assert.assertEquals(actualError, exceptedError,
                "If FAIL: Registration successful or error text: '" + actualError + "' is Incorrect");
    }

    @Severity(SeverityLevel.BLOCKER)
    @Story("US_01.003 Password Requirements")
    @Description("TC_01.003.04 Validate Password Meets All Requirements")
    @Test
    public void testPasswordMeetsAllRequirements() {
        String homePage = new RegisterAccountPage(getDriver())
                .setEmail(getFaker().internet().safeEmailAddress())
                .setPassword(PASSWORD_DEFAULT)
                .setPasswordConfirm(PASSWORD_DEFAULT)
                .clickSubmit(new HomePage(getDriver()))
                .getHeadline();

        Allure.step("The registration is successful, and the user will be redirected to the home page");
        Assert.assertEquals(homePage, "Welcome to Firefly III!",
                "If FAIL: Registration Failed, or user is Not on the home page");
    }

    @Ignore
    @Severity(SeverityLevel.CRITICAL)
    @Story("US_01.003 Password Requirements")
    @Description("TC_01.003.05 Validate Password Missing Uppercase Letter")
    @Test
    public void testPasswordMissingUppercaseLetter() {
        final String passWithoutUppercase = "9^gxh+8k#3k^xu(b";
        final String exceptedError = """
                Error! There were some problems with your input.
                                
                Password must contain at least one uppercase.""";

        String actualError = new RegisterAccountPage(getDriver())
                .setEmail(getFaker().internet().safeEmailAddress())
                .setPassword(passWithoutUppercase)
                .setPasswordConfirm(passWithoutUppercase)
                .clickSubmit(new RegisterAccountPage(getDriver()))
                .getErrorMessage();

        Allure.step(String.format("The registration is not submitted, an error message is displayed '%s' indicating " +
                "indicating that the password must include at least one uppercase letter", exceptedError));
        Assert.assertEquals(actualError, exceptedError,
                "If FAIL: Registration successful or error text: '" + actualError + "' is Incorrect");
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("US_01.005 Password Security Features")
    @Description("TC_01.005.04 Validate Password Masking ")
    @Test
    public void testPasswordMasking() {
        String registerPage = new RegisterAccountPage(getDriver())
                .setPassword(PASSWORD_DEFAULT)
                .getTypePassword();

        Allure.step("The entered password should be masked (e.g., shown as dots) for security purposes");
        Assert.assertEquals(registerPage, "password",
                "If FAIL: The password is not masked");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Story("US_01.002 Error Handling for Registration Fields")
    @Description("TC_01.002.05 Validate Different Passwords in Confirmation Field")
    @Test
    public void testDifferentPasswordsInConfirmationField() {
        String exceptedError = """
                Error! There were some problems with your input.
                
                The password confirmation does not match.""";

        String actualError = new RegisterAccountPage(getDriver())
                .setEmail(getFaker().internet().safeEmailAddress())
                .setPassword("rfu$znU7GpD5d)&Z")
                .setPasswordConfirm("v#q3$#dg4&kmXB%u")
                .clickSubmit(new RegisterAccountPage(getDriver()))
                .getErrorMessage();

        Allure.step(String.format("The registration fails, and an error message is displayed '%s' indicates that the " +
                "passwords entered are different", exceptedError));
        Assert.assertEquals(actualError, exceptedError,
                "If FAIL: Registration successful or error text: '" + actualError + "' is Incorrect");
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("US_01.004 Password Security Features")
    @Description("TC_01.004.01 Validate the 'Verify the Password Security' Feature")
    @Test
    public void testPasswordSecurityFeature() {
        String insecurePassword = "aaaaaaaaaaaaaaaa";
        String exceptedError = """
                Error! There were some problems with your input.
                
                This is not a secure password. Please try again. For more information, visit https://bit.ly/FF3-password-security""";

        String actualError = new RegisterAccountPage(getDriver())
                .setEmail(getFaker().internet().safeEmailAddress())
                .setPassword(insecurePassword)
                .setPasswordConfirm(insecurePassword)
                .clickSubmit(new RegisterAccountPage(getDriver()))
                .getErrorMessage();

        Allure.step(String.format("The registration fails, and an error message is displayed '%s' indicates that " +
                "the password entered is not secure", exceptedError));
        Assert.assertEquals(actualError, exceptedError,
                "If FAIL: Registration successful or error text: '" + actualError + "' is Incorrect");
    }

    @Severity(SeverityLevel.MINOR)
    @Story("US_01.004 Password Security Features")
    @Description("TC_01.004.02 Validate 'Verify Password Security' is Selected by Default")
    @Test
    public void testPasswordSecurityIsSelectedByDefault() {
        boolean isCheckboxEnable = new RegisterAccountPage(getDriver())
                .isSelectedVerifyPassword();

        Allure.step("'Verify password security' checkbox option is selected by default");
        Assert.assertTrue(isCheckboxEnable,
                "If FAIL: 'Verify password security' checkbox option is NOT selected by default");
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Story("US_01.004 Password Security Features")
    @Description("TC_01.004.03 Validate the Additional Information Button 'Check your password security'")
    @Test
    public void testAdditionalInformationButtonCheckYourPasswordSecurity() {
        boolean isTooltipDisplay = new RegisterAccountPage(getDriver())
                .clickQuestionCircle()
                .isTooltipDisplay();

        Allure.step("Tooltip has opened on how to choose a strong password");
        Assert.assertTrue(isTooltipDisplay,
                "If FAIL: Tooltip is NOT opened on how to choose a strong password");

    }

    @Severity(SeverityLevel.CRITICAL)
    @Story("US_01.007 Handling Special Cases in Registration Fields")
    @Description("TC_01.007.02 Validate Duplicate Email Registration Attempt")
    @Test
    public void testDuplicateEmailRegistrationAttempt() {
        String exceptedError = """
                Error! There were some problems with your input.
                
                The email address has already been taken.""";

        String actualError = new RegisterAccountPage(getDriver())
                .setEmail(EMAIL_DEFAULT)
                .setPassword(PASSWORD_DEFAULT)
                .setPasswordConfirm(PASSWORD_DEFAULT)
                .clickSubmit(new RegisterAccountPage(getDriver()))
                .getErrorMessage();

        Allure.step(String.format("The registration fails, and an error message is displayed '%s' indicating that the " +
                "email address is already in use", exceptedError));
        Assert.assertEquals(actualError, exceptedError,
                "If FAIL: Registration successful or error text: '" + actualError + "' is Incorrect");
    }

    @Ignore
    @Severity(SeverityLevel.NORMAL)
    @Story("US_01.007 Handling Special Cases in Registration Fields")
    @Description("TC_01.007.03 Validate Attempt to Register Without Agreeing to Terms")
    @Test
    public void testAttemptRegisterWithoutAgreeingTerms() {
        String exceptedError = """
                Error! There were some problems with your input.
                                
                You must Agree with the Privacy Policy and Terms of Service.""";

        String actualError = new RegisterAccountPage(getDriver())
                .setEmail(getFaker().internet().safeEmailAddress())
                .setPassword(PASSWORD_DEFAULT)
                .setPasswordConfirm(PASSWORD_DEFAULT)
                .clickPrivacyPolicy()
                .clickSubmit(new RegisterAccountPage(getDriver()))
                .getErrorMessage();

        Allure.step(String.format("The registration fails, and an error message is displayed '%s' indicating that " +
                "the user must agree to the terms before registering", exceptedError));
        Assert.assertEquals(actualError, exceptedError,
                "If FAIL: Registration successful or error text: '" + actualError + "' is Incorrect");
    }

}
