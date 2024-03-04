package page.Budgets;

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
}
