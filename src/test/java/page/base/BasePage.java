package page.base;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import page.bills.BillsPage;
import page.budgets.BudgetsPage;
import page.HomePage;

import java.time.Duration;

public abstract class BasePage extends BaseModel {
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

    public BasePage(WebDriver driver) {
        super(driver);
    }

    public String headline() {
        return headline.getText();
    }

    @Step("Click 'Budgets' button on sidebar")
    public BudgetsPage goBudgets() {
        budgetsSidePanel.click();

        return new BudgetsPage(getDriver());
    }

    public HomePage goHomePage() {
        logoFirefly.click();

        return new HomePage(getDriver());
    }

    public BillsPage goBill() {
        billsSidePanel.click();

        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        if (getDriver().findElements(By.className("introjs-overlay")).size() > 0) {
            buttonSkip.click();
        }

        return new BillsPage(getDriver());
    }
}
