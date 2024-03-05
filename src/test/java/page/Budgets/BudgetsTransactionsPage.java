package page.Budgets;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import page.Base.BasePage;

public class BudgetsTransactionsPage extends BasePage {

    @FindBy(className = "money-positive")
    private WebElement amountText;

    public BudgetsTransactionsPage(WebDriver driver) {
        super(driver);
    }

    public String getAmountText() {
        getWait().until(driver -> amountText.isDisplayed());
        return amountText.getText();
    }
}
