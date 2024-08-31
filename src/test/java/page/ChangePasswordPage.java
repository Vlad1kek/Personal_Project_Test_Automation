package page;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import page.base.BasePage;

public class ChangePasswordPage extends BasePage<ChangePasswordPage> {
    @FindBy(id = "inputOldPassword")
    private WebElement fieldOldPassword;

    @FindBy(id = "inputNewPassword1")
    private WebElement fieldNewPassword;

    @FindBy(id = "inputNewPassword2")
    private WebElement fieldNewPasswordAgain;

    @FindBy(className = "btn-success")
    private WebElement buttonChangeYourPassword;

    public ChangePasswordPage(WebDriver driver) {
        super(driver);
    }

    @Step("Enter the password in the 'Current password' field")
    public ChangePasswordPage setCurrentPassword(String currentPassword) {
        fieldOldPassword.sendKeys(currentPassword);

        return this;
    }

    @Step("Enter the NEW password in the 'New password' field")
    public ChangePasswordPage setNewPassword(String newPassword) {
        fieldNewPassword.sendKeys(newPassword);

        return this;
    }

    @Step("Enter the NEW password in the 'New password (again)' field")
    public ChangePasswordPage setNewPasswordAgain(String newPassword) {
        fieldNewPasswordAgain.sendKeys(newPassword);

        return this;
    }

    @Step("Click on the 'Change your password' button")
    public ProfilePage clickChangeYourPassword() {
        buttonChangeYourPassword.click();

        return new ProfilePage(getDriver());
    }
}
