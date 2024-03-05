package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import page.Base.BasePage;

public class HomePage extends BasePage {

    @FindBy(xpath = "//div[@id='box-left-to-spend-box']/div/span[2]")
    private WebElement boxLeftToSpendNumber;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public String getLeftToSpendNumber() {

        return getWait().until(ExpectedConditions.visibilityOf(boxLeftToSpendNumber)).getText();
    }
}
