package page.base;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import page.bills.BillsDetailsPage;
import page.budgets.BudgetsDetailsPage;
import page.HomePage;
import page.login.LoginPage;

import java.time.Duration;

public abstract class BasePage<Self extends BasePage<?>> extends BaseModel {
    @FindBy(tagName = "h1")
    private WebElement headline;

    @FindBy(css = "li[id='budget-menu']")
    private WebElement budgetsSidePanel;

    @FindBy(className = "logo-lg")
    private WebElement logoFirefly;

    @FindBy(css = "a[href$='/bills']")
    private WebElement billsSidePanel;

    @FindBy(className = "introjs-skipbutton")
    private WebElement buttonSkip;

    @FindBy(className = "btn-success")
    private WebElement submit;

    @FindBy(css = "a[href$='logout']")
    private WebElement logout;

    @FindBy(className = "login-box-msg")
    private WebElement loginBoxMsg;

    public BasePage(WebDriver driver) {
        super(driver);
    }

    public String getHeadline() {
        return headline.getText();
    }

    @Step("Click on 'Budgets' button on sidebar")
    public BudgetsDetailsPage goBudgets() {
        budgetsSidePanel.click();

        return new BudgetsDetailsPage(getDriver());
    }

    public HomePage goHomePage() {
        logoFirefly.click();

        return new HomePage(getDriver());
    }

    public BillsDetailsPage goBill() {
        billsSidePanel.click();

        return new BillsDetailsPage(getDriver());
    }

    public BillsDetailsPage clickSubmit() {
        submit.click();

        return new BillsDetailsPage(getDriver());
    }

    @Step("Skip tutorial.")
    public Self skipTutorial() {
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        if (getDriver().findElements(By.className("introjs-overlay")).size() > 0) {
            buttonSkip.click();
        }

        return (Self)this;
    }

    @Step("Close and reopen the browser")
    public HomePage closeDriverAndOpenURL() {
        getDriver().navigate().to("http://localhost");

        return new HomePage(getDriver());
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

    @Step("Click on the 'Logout' on the sidebar")
    public LoginPage clickLogout() {
        logout.click();

        return new LoginPage(getDriver());
    }

    public String getLoginBoxMsg() {
        return loginBoxMsg.getText();
    }
}
