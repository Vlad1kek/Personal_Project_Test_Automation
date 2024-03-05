package page.Budgets;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import page.Base.BasePage;

import java.time.Duration;

public class BudgetsTransactionsPage extends BasePage {

    @FindBy(className = "money-positive")
    private WebElement amountText;

    public BudgetsTransactionsPage(WebDriver driver) {
        super(driver);
    }

    public String getAmountText() {
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        getDriver().navigate().refresh();

        return amountText.getText();
    }
}
