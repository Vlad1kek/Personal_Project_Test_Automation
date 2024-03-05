package page.Budgets;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import page.Base.BasePage;

public class BudgetsTransactionsPage extends BasePage {

    @FindBy(className = "money-positive")
    private WebElement amountText;

    public BudgetsTransactionsPage(WebDriver driver) {
        super(driver);
    }

    public String getAmountText() {
        getDriver().navigate().refresh();
        return getWait().until(ExpectedConditions.visibilityOf(amountText)).getText();
    }
}
