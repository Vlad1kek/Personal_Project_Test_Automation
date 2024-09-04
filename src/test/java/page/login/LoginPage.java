package page.login;

import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import page.HomePage;
import page.base.BaseLoginPage;


public class LoginPage extends BaseLoginPage<LoginPage> {

    @FindBy(name = "password")
    private WebElement fieldPassword;

    @FindBy(id = "remember")
    private WebElement checkboxRememberMe;

    @FindBy(css = "a[href$='reset']")
    private WebElement buttonForgotPassword;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Enter password in the password field")
    public LoginPage setPassword(String password) {
        fieldPassword.clear();
        fieldPassword.sendKeys(password);

        return this;
    }

    public String getPasswordFieldType() {
        return fieldPassword.getAttribute("type");
    }

    @Step("Select the 'Remember Me' checkbox")
    public LoginPage setRememberMe() {
        getAction().click(checkboxRememberMe)
                .perform();

        return this;
    }

    @Step("Click on the 'Forgot Password?' link on the login page")
    public ResetPasswordPage clickForgotPassword() {
        buttonForgotPassword.click();

        return new ResetPasswordPage(getDriver());
    }

    @Step("1. Press ''Tab' keyboard key until the control comes to the email address text field and enter the valid email address" +
            "2. Press 'Tab' keyboard key to move the control to password text field and enter the valid password" +
            "3. Press 'Tab' keyboard key until the control comes 'Sign in' button and press 'Enter' key to submit")
    public HomePage loggingIntoUsingKeyboard(String email, String password) {
        getAction()
                .sendKeys(email)
                .sendKeys(Keys.TAB)
                .sendKeys(password)
                .sendKeys(Keys.TAB)
                .sendKeys(Keys.TAB)
                .sendKeys(Keys.ENTER)
                .perform();

        return new HomePage(getDriver());
    }

    public String getPasswordPlaceholder() {
        return fieldPassword.getAttribute("placeholder");
    }
}
