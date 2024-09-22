package page.base;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class BaseEditPage<Self extends BaseEditPage<?>> extends BasePage<Self> {
    @FindBy(className = "btn-success")
    private WebElement submit;

    @FindBy(name = "name")
    private WebElement nameInput;

    public BaseEditPage(WebDriver driver) {
        super(driver);
    }

    @Step("Click on the Submit button")
    public <T> T clickSubmit(T page) {
        submit.click();

        return page;
    }

    @Step("In the 'Name' field, enter new valid name")
    public Self setName(String name) {
        nameInput.sendKeys(name);

        return (Self)this;
    }
}
