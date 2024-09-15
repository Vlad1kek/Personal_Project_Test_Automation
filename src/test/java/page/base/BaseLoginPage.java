package page.base;

import io.qameta.allure.Step;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import page.HomePage;
import page.login.LoginPage;

public abstract class BaseLoginPage<Self extends BaseLoginPage<?>> extends BaseModel {
    @FindBy(name = "email")
    private WebElement emailField;

    @FindBy(className = "btn-block")
    private WebElement buttonSubmit;

    @FindBy(className = "alert-danger")
    private WebElement errorAlert;

    @FindBy(className = "login-box-msg")
    private WebElement loginBoxMsg;

    public BaseLoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Click on Browser back button")
    public <T> T clickBackBrowserButton(T page) {
        getDriver().navigate().back();

        return page;
    }

    @Step("Refresh page")
    public <T> T refreshPage(T page) {
        getDriver().navigate().refresh();

        return page;
    }

    public String getUrlAddress() {
        return getDriver().getCurrentUrl();
    }

    @Step("Enter email address in the email field")
    public Self setEmail(String email) {
        emailField.clear();
        emailField.sendKeys(email);

        return (Self)this;
    }

    public String getEmailPlaceholder() {
        return emailField.getAttribute("placeholder");
    }

    @Step("Click on the submit button")
    public <T> T clickSubmit(T page) {
        buttonSubmit.click();

        return page;
    }

    public String getErrorMessage() {
        return errorAlert.getText();
    }

    @Step("Enter the text")
    public Self enterText(String text) {
        getAction()
                .sendKeys(text)
                .perform();

        return (Self)this;
    }

    @Step("Press 'Tab' keyboard key to move")
    public Self pressTab() {
        getAction()
                .sendKeys(Keys.TAB)
                .perform();

        return (Self)this;
    }

    @Step("Press 'Enter' key to submit")
    public HomePage pressEnter() {
        getAction()
                .sendKeys(Keys.ENTER)
                .perform();

        return new HomePage(getDriver());
    }

    public String getLoginBoxMsg() {
        return loginBoxMsg.getText();
    }

    @Step("Go to protected URL")
    public LoginPage followTheURL(String path) {
        getDriver().navigate().to("http://localhost/" + path);

        return new LoginPage(getDriver());
    }

    public boolean doesCookieExist(String cookie) {
        return getDriver().manage().getCookieNamed(cookie) == null;
    }

    public String getLocalStorage(String token) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        return (String) js.executeScript(String.format("return window.localStorage.getItem('%s');", token));
    }

    public String getSessionStorage(String token) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        return (String) js.executeScript(String.format("return window.sessionStorage.getItem('%s');", token));
    }

}
