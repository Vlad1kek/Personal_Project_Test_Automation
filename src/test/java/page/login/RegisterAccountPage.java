package page.login;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import page.base.BaseLoginPage;

public class RegisterAccountPage extends BaseLoginPage<RegisterAccountPage> {
    @FindBy(name = "password")
    private WebElement fieldPassword;

    @FindBy(name = "password_confirmation")
    private WebElement fieldPasswordConfirm;

    @FindBy(id = "verify_password")
    private WebElement verifyPassword;

    @FindBy(css = "a[data-toggle='modal']")
    private WebElement questionCircle;

    @FindBy(id = "passwordModal")
    private WebElement tooltipQuestionCircle;

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

    public String getTypePassword() {
        return fieldPassword.getAttribute("type");
    }

    public boolean isSelectedVerifyPassword() {
        return verifyPassword.isSelected();
    }

    public RegisterAccountPage clickQuestionCircle() {
        questionCircle.click();

        return this;
    }

    public boolean isTooltipDisplay() {
        return getWait().until(ExpectedConditions.visibilityOf(tooltipQuestionCircle))
                .isDisplayed();
    }

    @Step("Click on the 'Agree to Privacy Policy and Terms of Service'")
    public RegisterAccountPage clickPrivacyPolicy() {
        // not have Privacy Policy checkbox

        return this;
    }
}
