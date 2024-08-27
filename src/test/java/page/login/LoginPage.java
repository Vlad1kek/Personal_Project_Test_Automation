package page.login;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import page.base.BaseLoginPage;


public class LoginPage extends BaseLoginPage {

    @FindBy(name = "email")
    private WebElement emailFormControl;

    @FindBy(name = "password")
    private WebElement passwordFormControl;

    @FindBy(className = "btn-block")
    private WebElement buttonSignIn;

    @FindBy(className = "alert-danger")
    private WebElement errorAlert;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Enter email address in the email field")
    public LoginPage setEmail(String email) {
        emailFormControl.sendKeys(email);

        return new LoginPage(getDriver());
    }

    @Step("Enter password in the password field")
    public LoginPage setPassword(String password) {
        passwordFormControl.sendKeys(password);

        return new LoginPage(getDriver());
    }

    @Step("Click on the 'Sing in' button")
    public <T> T clickSignIn(T page) {
        buttonSignIn.click();

        return page;
    }

    public String getErrorMessage() {
       return errorAlert.getText();
    }
}
