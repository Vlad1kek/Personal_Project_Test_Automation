package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BasePage extends BaseModel {
    @FindBy(tagName = "h1")
    private WebElement headline;

    public BasePage(WebDriver driver) {
        super(driver);
    }

    public BasePage first() {
        getDriver().findElement(By.cssSelector("a[href$='budgets']")).click();

        return this;
    }

    public String headline() {
        return headline.getText();
    }
}
