package page.budgets;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import page.base.BasePage;

import java.time.Duration;

public class CreateBudgetsPage extends BasePage {

    @FindBy(id = "ffInput_name")
    private WebElement inputName;

    @FindBy(className = "btn-success")
    private WebElement buttonSubmit;

    @FindBy(className = "introjs-skipbutton")
    private WebElement buttonSkip;

    @FindBy(id = "ffInput_auto_budget_type")
    private WebElement optionAutoBudget;

    @FindBy(id = "ffInput_auto_budget_amount")
    private WebElement autoBudgetAmount;

    public CreateBudgetsPage(WebDriver driver) {
        super(driver);
    }

    @Step("Set Name")
    public CreateBudgetsPage setName(String name) {
        inputName.sendKeys(name);

        return this;
    }

    @Step("Click 'Store new budget' button")
    public BudgetsPage submit() {
        buttonSubmit.click();

        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        if (getDriver().findElements(By.className("introjs-overlay")).size() > 0) {
            buttonSkip.click();
        }

        return new BudgetsPage(getDriver());
    }

    @Step("In the auto budget selector, select 'Set a fixed amount every period'")
    public CreateBudgetsPage setAFixedAmountEveryPeriod()  {
        Select select = new Select(optionAutoBudget);
        select.selectByValue("1");

        return this;
    }

    @Step("In the auto budget selector, select 'Add an amount every period'")
    public CreateBudgetsPage setAddAnAmountEveryPeriod()  {
        Select select = new Select(optionAutoBudget);
        select.selectByValue("2");

        return this;
    }

    @Step("In the auto budget selector, select 'Add an amount every period and correct for overspending'")
    public CreateBudgetsPage setCorrectForOverspending()  {
        Select select = new Select(optionAutoBudget);
        select.selectByValue("3");

        return this;
    }

    @Step("In the 'Auto-budget amount' field, enter valid numbers,")
    public CreateBudgetsPage setAutoBudgetAmount(String amount) {
        getAction().click(autoBudgetAmount)
                .sendKeys(amount)
                .perform();

        return this;
    }
}
