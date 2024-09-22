package page.bills;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import page.base.BaseEditPage;

public class BillsEditPage extends BaseEditPage<BillsEditPage> {
    @FindBy(name = "amount_min")
    private WebElement minimumAmountInput;

    @FindBy(name = "amount_max")
    private  WebElement maximumAmountInput;

    @FindBy(id = "ffInput_repeat_freq")
    private WebElement optionsRepeats;

    public BillsEditPage(WebDriver driver) {
        super(driver);
    }

    @Step("In the 'Minimum amount' field, enter a valid amount")
    public BillsEditPage setMinimumAmount(String amount) {
        minimumAmountInput.clear();
        minimumAmountInput.sendKeys(amount);

        return this;
    }

    @Step("In the 'Maximum amount' field, enter a valid amount")
    public BillsEditPage setMaximumAmount(String amount) {
        maximumAmountInput.clear();
        maximumAmountInput.sendKeys(amount);

        return this;
    }

    @Step("Set the frequency to repeat 'weekly'")
    public BillsEditPage setRepeatsWeekly() {
        new Select(optionsRepeats).selectByValue("weekly");

        return this;
    }

    @Step("Set the frequency to repeat 'daily'")
    public BillsEditPage setRepeatsDaily() {
        new Select(optionsRepeats).selectByValue("daily");

        return this;
    }

    @Step("Set the frequency to repeat 'yearly'")
    public BillsEditPage setRepeatsYearly() {
        new Select(optionsRepeats).selectByValue("yearly");

        return this;
    }

    @Step("Set the frequency to repeat 'every half-year'")
    public BillsEditPage setRepeatsHalfYear() {
        new Select(optionsRepeats).selectByValue("half-year");

        return this;
    }

    @Step("Set the frequency to repeat 'every quarter'")
    public BillsEditPage setRepeatsQuarterly() {
        new Select(optionsRepeats).selectByValue("quarterly");

        return this;
    }
}
