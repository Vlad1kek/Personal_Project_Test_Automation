package page.bills;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import page.base.BasePage;

public class EditBillPage extends BasePage {
    @FindBy(id = "ffInput_repeat_freq")
    private WebElement optionsRepeats;

    @FindBy(className = "btn-success")
    private WebElement submit;

    public EditBillPage(WebDriver driver) {
        super(driver);
    }

    public EditBillPage setRepeatsWeekly() {
        new Select(optionsRepeats).selectByValue("weekly");

        return this;
    }

    public EditBillPage setRepeatsDaily() {
        new Select(optionsRepeats).selectByValue("daily");

        return this;
    }

    public EditBillPage setRepeatsYearly() {
        new Select(optionsRepeats).selectByValue("yearly");

        return this;
    }

    public EditBillPage setRepeatsHalfYear() {
        new Select(optionsRepeats).selectByValue("half-year");

        return this;
    }

    public BillsPage clickSubmit() {
        submit.click();

        return new BillsPage(getDriver());
    }
}
