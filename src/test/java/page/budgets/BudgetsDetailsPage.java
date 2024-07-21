package page.budgets;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import page.base.BaseDetailsPage;
import page.bills.BillsEditPage;

public class BudgetsDetailsPage extends BaseDetailsPage<BudgetsEditPage, BudgetsDetailsPage>{
    @FindBy(xpath = "//input[@type='number']")
    private WebElement budgetAmount;

    @FindBy(css = "span[class$='budgeted_amount']")
    private WebElement budgetedAmountText;

    @FindBy(xpath = "//div[starts-with(@class, 'col-lg-8')]/div/a[2]")
    private WebElement valueBudgetsMonthYear;

    public BudgetsDetailsPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected BudgetsEditPage createEditPage() {
        return new BudgetsEditPage(getDriver());
    }

    @Step("Enter the amount in the 'Budgets' column on the created budget line.")
    public BudgetsDetailsPage setBudgetAmount(String amountNumber) {
        getAction().click(budgetAmount)
                .keyDown(Keys.CONTROL)
                .sendKeys("a")
                .keyUp(Keys.CONTROL)
                .sendKeys(amountNumber)
                .sendKeys(Keys.ENTER)
                .pause(500)
                .perform();

        return this;
    }

    public BudgetsTransactionsPage clickBudgetNameInList(String name) {
        getDriver().findElement(By.xpath("//a[contains(text(), '" + name + "')]")).click();

        return new BudgetsTransactionsPage(getDriver());
    }

    public String getBudgetedAmountText() {
        return budgetedAmountText.getText();
    }

    public String getValueBudgetsMonthYear() {
        return valueBudgetsMonthYear.getText();
    }

    public String getCalendarCheckTitle(String nameBudget) {
       return getDriver().findElement(By.xpath("//td[contains(@data-value,'" + nameBudget + "')]/span"))
                .getAttribute("title");
    }

    public BudgetsEditPage clickPencil(String name) {
        getDriver().findElement(By.xpath(" //td[contains(@data-value, '" + name + "')]/parent::tr/td/div/a[2]")).click();

        return new BudgetsEditPage(getDriver());
    }
}
