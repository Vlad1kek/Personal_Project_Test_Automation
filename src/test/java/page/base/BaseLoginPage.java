package page.base;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

public abstract class BaseLoginPage extends BaseModel {
    public BaseLoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Click on Browser back button")
    public <T> T clickBackBrowserButton(T page) {
        getDriver().navigate().back();

        return page;
    }

    @Step("Refresh page")
    public <T> T refreshPage(T page) {
        getDriver().navigate().refresh();

        return page;
    }
}
