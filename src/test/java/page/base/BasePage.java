package page.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import page.bills.BillsPage;
import page.budgets.BudgetsPage;
import page.HomePage;

public class BasePage extends BaseModel {
    @FindBy(tagName = "h1")
    private WebElement headline;

    @FindBy(css = "li[id='budget-menu']")
    private WebElement budgetsSidePanel;

    @FindBy(className = "logo-lg")
    private WebElement logoFirefly;

    @FindBy(css = "a[href$='/bills']")
    private WebElement billsSidePanel;

    public BasePage(WebDriver driver) {
        super(driver);
    }

    public String headline() {
        return headline.getText();
    }

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

        return new BillsPage(getDriver());
    }
}
