package page.base;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import page.bills.BillsDetailsPage;
import page.budgets.BudgetsDetailsPage;

import java.util.Collections;
import java.util.List;

public abstract class BaseDetailsPage<ProjectEditPage extends BaseEditPage<?>, Self extends BaseDetailsPage<?, ?>> extends BasePage<Self> {
    @FindBy(className = "btn-success")
    private WebElement createButton;

    @FindBy(xpath = "//a[starts-with(@href,'http://localhost/bills/show/')]")
    private List<WebElement> billsList;

    @FindBy(xpath = "//a[starts-with(@href,'http://localhost/budgets/show/')]")
    private List<WebElement> budgetsList;
    public BaseDetailsPage(WebDriver driver) {
        super(driver);
    }

    protected abstract ProjectEditPage createEditPage();

    @Step("Click 'Create' button in the middle")
    public ProjectEditPage clickCreateButton() {
        createButton.click();

        return createEditPage();
    }
    public List<String> getNamesList() {
        if (this instanceof BillsDetailsPage) {
            return billsList.stream().map(WebElement::getText).toList();
        } else if (this instanceof BudgetsDetailsPage){
            return budgetsList.stream().map(WebElement::getText).toList();
        }
        return Collections.emptyList();
    }
}


