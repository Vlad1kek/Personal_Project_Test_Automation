package page.Budgets;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import page.Base.BasePage;

import java.util.List;

public class BudgetsPage extends BasePage {

    @FindBy(className = "btn-success")
    private WebElement createBudgets;

    @FindBy(xpath = "//a[starts-with(@href,'http://localhost/budgets/show/')]")
    private List<WebElement> budgetsList;

    @FindBy(xpath = "//input[@type='number']")
    private WebElement budgetAmount;

    @FindBy(xpath = "//a[contains(text(), 'NewTestBudgets123')]")
    private WebElement budgetLink;

    public BudgetsPage(WebDriver driver) {
        super(driver);
    }

    public CreateBudgetsPage clickCreateBudgets(){
        createBudgets.click();

        return new CreateBudgetsPage(getDriver());
    }

    public List<String> getBudgetsNamesText() {
        return budgetsList.stream().map(WebElement::getText).toList();
    }

    public BudgetsPage setBudgetAmount(String amountNumber) {
        getAction().click(budgetAmount)
                .doubleClick(budgetAmount)
                .sendKeys("1")
                .sendKeys(Keys.ENTER)
                .doubleClick(budgetAmount)
                .sendKeys(amountNumber)
                .pause(2000)
                .perform();

        return this;
    }

    public BudgetsTransactionsPage clickBudgetNameInList(String name) {
        getDriver().findElement(By.xpath("//a[contains(text(), '" + name + "')]")).click();

        return new BudgetsTransactionsPage(getDriver());
    }
}
