package page.budgets;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import page.base.BasePage;

public class BudgetsTransactionsPage extends BasePage<BudgetsTransactionsPage> {
    @FindBy(className = "money-positive")
    private WebElement amountText;

    public BudgetsTransactionsPage(WebDriver driver) {
        super(driver);
    }

    public String getAmountText() {
        return getWait().until(ExpectedConditions.visibilityOf(amountText)).getText();
    }
}
