package page.bills;

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

    public BillsEditPage setMinimumAmount(String amount) {
        minimumAmountInput.sendKeys(amount);

        return this;
    }

    public BillsEditPage setMaximumAmount(String amount) {
        maximumAmountInput.sendKeys(amount);

        return this;
    }

    public BillsEditPage setRepeatsWeekly() {
        new Select(optionsRepeats).selectByValue("weekly");

        return this;
    }

    public BillsEditPage setRepeatsDaily() {
        new Select(optionsRepeats).selectByValue("daily");

        return this;
    }

    public BillsEditPage setRepeatsYearly() {
        new Select(optionsRepeats).selectByValue("yearly");

        return this;
    }

    public BillsEditPage setRepeatsHalfYear() {
        new Select(optionsRepeats).selectByValue("half-year");

        return this;
    }

    public BillsEditPage setRepeatsQuarterly() {
        new Select(optionsRepeats).selectByValue("quarterly");

        return this;
    }
}
