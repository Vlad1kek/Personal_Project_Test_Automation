package page.login;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import page.base.BaseLoginPage;

public class ResetPasswordPage extends BaseLoginPage<ResetPasswordPage> {

    @FindBy(className = "login-box-msg")
    private WebElement headLine;

    public ResetPasswordPage(WebDriver driver) {
        super(driver);
    }

    public String getHeadLine() {
        return headLine.getText();
    }
}
