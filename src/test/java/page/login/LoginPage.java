package page.login;

import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import page.HomePage;
import page.base.BaseLoginPage;


public class LoginPage extends BaseLoginPage {

    @FindBy(name = "email")
    private WebElement emailField;

    @FindBy(name = "password")
    private WebElement passwordField;

    @FindBy(className = "btn-block")
    private WebElement buttonSignIn;

    @FindBy(className = "alert-danger")
    private WebElement errorAlert;

    @FindBy(id = "remember")
    private WebElement checkboxRememberMe;

    @FindBy(css = "a[href$='reset']")
    private WebElement buttonForgotPassword;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Enter email address in the email field")
    public LoginPage setEmail(String email) {
        emailField.clear();
        emailField.sendKeys(email);

        return this;
    }

    @Step("Enter password in the password field")
    public LoginPage setPassword(String password) {
        passwordField.clear();
        passwordField.sendKeys(password);

        return this;
    }

    @Step("Click on the 'Sing in' button")
    public <T> T clickSignIn(T page) {
        buttonSignIn.click();

        return page;
    }

    public String getPasswordFieldType() {
        return passwordField.getAttribute("type");
    }

    public LoginPage setRememberMe() {
        getAction().click(checkboxRememberMe)
                .perform();

        return this;
    }

    public String getErrorMessage() {
       return errorAlert.getText();
    }

    public ResetPasswordPage clickForgotPassword() {
        buttonForgotPassword.click();

        return new ResetPasswordPage(getDriver());
    }

    @Step("1. Press ''Tab' keyboard key until the control comes to the email address text field and enter the valid email address" +
            "2. Press 'Tab' keyboard key to move the control to password text field and enter the valid password" +
            "3. Press 'Tab' keyboard key until the control comes 'Sign in' button and press 'Enter' key to submit")
    public HomePage loggingIntoUsingKeyboard(String email, String password) {
        getAction().sendKeys(email)
                .sendKeys(Keys.TAB)
                .sendKeys(password)
                .sendKeys(Keys.TAB)
                .sendKeys(Keys.TAB)
                .sendKeys(Keys.ENTER)
                .perform();

        return new HomePage(getDriver());
    }
}
