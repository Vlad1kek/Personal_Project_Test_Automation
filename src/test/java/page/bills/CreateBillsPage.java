package page.bills;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import page.base.BasePage;

public class CreateBillsPage extends BasePage {
    @FindBy(name = "name")
    private WebElement nameInput;

    @FindBy(name = "amount_min")
    private WebElement minimumAmountInput;

    @FindBy(name = "amount_max")
    private  WebElement maximumAmountInput;

    @FindBy(className = "btn-success")
    private WebElement buttonSubmit;

    public CreateBillsPage(WebDriver driver) {
        super(driver);
    }

    public CreateBillsPage setName(String name) {
        nameInput.sendKeys(name);

        return this;
    }

    public CreateBillsPage setMinimumAmount(String amount) {
        minimumAmountInput.sendKeys(amount);

        return this;
    }

    public CreateBillsPage setMaximumAmount(String amount) {
        maximumAmountInput.sendKeys(amount);

        return this;
    }

    public RulesBillsPage submit() {
        buttonSubmit.click();

        return new RulesBillsPage(getDriver());
    }
}
