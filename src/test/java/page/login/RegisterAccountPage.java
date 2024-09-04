package page.login;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import page.base.BaseLoginPage;

public class RegisterAccountPage extends BaseLoginPage<RegisterAccountPage> {
    @FindBy(name = "password")
    private WebElement fieldPassword;

    @FindBy(name = "password_confirmation")
    private WebElement fieldPasswordConfirm;

    public RegisterAccountPage(WebDriver driver) {
        super(driver);
    }

    @Step("Enter a password in the password field")
    public RegisterAccountPage setPassword(String password) {
        fieldPassword.clear();
        fieldPassword.sendKeys(password);

        return this;
    }

    @Step("Enter a password in the password(again) field")
    public RegisterAccountPage setPasswordConfirm(String password) {
        fieldPasswordConfirm.clear();
        fieldPasswordConfirm.sendKeys(password);

        return this;
    }

    public String getPasswordPlaceholder() {
        return fieldPassword.getAttribute("placeholder");
    }

    public String getPasswordConfirmPlaceholder() {
        return fieldPasswordConfirm.getAttribute("placeholder");
    }

}
