package page.budgets;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import page.base.BaseEditPage;

public class BudgetsEditPage extends BaseEditPage<BudgetsEditPage> {
    @FindBy(className = "btn-success")
    private WebElement buttonSubmit;

    @FindBy(className = "introjs-skipbutton")
    private WebElement buttonSkip;

    @FindBy(id = "ffInput_auto_budget_type")
    private WebElement optionAutoBudget;

    @FindBy(id = "ffInput_auto_budget_amount")
    private WebElement autoBudgetAmount;

    public BudgetsEditPage(WebDriver driver) {
        super(driver);
    }

    @Step("In the auto budget selector, select 'Set a fixed amount every period'")
    public BudgetsEditPage setAFixedAmountEveryPeriod()  {
        Select select = new Select(optionAutoBudget);
        select.selectByValue("1");

        return this;
    }

    @Step("In the auto budget selector, select 'Add an amount every period'")
    public BudgetsEditPage setAddAnAmountEveryPeriod()  {
        Select select = new Select(optionAutoBudget);
        select.selectByValue("2");

        return this;
    }

    @Step("In the auto budget selector, select 'Add an amount every period and correct for overspending'")
    public BudgetsEditPage setCorrectForOverspending()  {
        Select select = new Select(optionAutoBudget);
        select.selectByValue("3");

        return this;
    }

    @Step("In the 'Auto-budget amount' field, enter valid numbers,")
    public BudgetsEditPage setAutoBudgetAmount(String amount) {
        getAction().click(autoBudgetAmount)
                .sendKeys(amount)
                .perform();

        return this;
    }
}
