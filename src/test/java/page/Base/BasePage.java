package page.Base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import page.Budgets.BudgetsPage;

public class BasePage extends BaseModel {
    @FindBy(tagName = "h1")
    private WebElement headline;

    @FindBy(xpath = "//ul[@class='sidebar-menu tree']/li[3]")
    private WebElement budgetsSidePanel;

    public BasePage(WebDriver driver) {
        super(driver);
    }

    public String headline() {
        return headline.getText();
    }

    public BudgetsPage clickBudgets() {
        budgetsSidePanel.click();

        return new BudgetsPage(getDriver());
    }

}
