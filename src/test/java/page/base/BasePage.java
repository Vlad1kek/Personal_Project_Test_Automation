package page.base;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import page.bills.BillsDetailsPage;
import page.budgets.BudgetsDetailsPage;
import page.HomePage;

import java.time.Duration;

public abstract class BasePage<Self extends BasePage<?>> extends BaseModel {
    @FindBy(tagName = "h1")
    private WebElement headline;

    @FindBy(css = "li[id='budget-menu']")
    private WebElement budgetsSidePanel;

    @FindBy(className = "logo-lg")
    private WebElement logoFirefly;

    @FindBy(css = "a[href$='/bills']")
    private WebElement billsSidePanel;

    @FindBy(className = "introjs-skipbutton")
    private WebElement buttonSkip;

    @FindBy(className = "btn-success")
    private WebElement submit;

    public BasePage(WebDriver driver) {
        super(driver);
    }

    public String headline() {
        return headline.getText();
    }

    @Step("Click 'Budgets' button on sidebar")
    public BudgetsDetailsPage goBudgets() {
        budgetsSidePanel.click();

        return new BudgetsDetailsPage(getDriver());
    }

    public HomePage goHomePage() {
        logoFirefly.click();

        return new HomePage(getDriver());
    }

    public BillsDetailsPage goBill() {
        billsSidePanel.click();

        return new BillsDetailsPage(getDriver());
    }

    public BillsDetailsPage clickSubmit() {
        submit.click();

        return new BillsDetailsPage(getDriver());
    }

    public Self skipTutorial() {
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        if (getDriver().findElements(By.className("introjs-overlay")).size() > 0) {
            buttonSkip.click();
        }

        return (Self)this;
    }

}
