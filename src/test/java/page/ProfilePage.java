package page;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import page.base.BasePage;

public class ProfilePage extends BasePage<ProfilePage> {
    @FindBy(css = "a[href$='change-password']")
    private WebElement buttonChangePassword;

    public ProfilePage(WebDriver driver) {
        super(driver);
    }

    @Step("Click on the 'Change your password' ")
    public ChangePasswordPage clickChangePassword() {
        buttonChangePassword.click();

        return new ChangePasswordPage(getDriver());
    }
}
