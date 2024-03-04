package page.Budgets;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import page.Base.BasePage;

public class CreateBudgetsPage extends BasePage {
    @FindBy(id = "ffInput_name")
    private WebElement inputName;

    @FindBy(className = "btn-success")
    private WebElement buttonSubmit;

    @FindBy(className = "introjs-skipbutton")
    private WebElement buttonSkip;

    public CreateBudgetsPage(WebDriver driver) {
        super(driver);
    }

    public CreateBudgetsPage setName(String name) {
        inputName.sendKeys(name);

        return this;
    }

    public BudgetsPage submit() {
        buttonSubmit.click();

        if (getDriver().findElements(By.className("introjs-overlay")).size() > 0) {
            buttonSkip.click();
        }

        return new BudgetsPage(getDriver());
    }
}
