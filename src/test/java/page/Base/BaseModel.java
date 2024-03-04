package page.Base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BaseModel {
    private final WebDriver driver;
    private Actions action;

    protected Wait<WebDriver> wait = new WebDriverWait(getDriver(), Duration.ofSeconds(2));

    public Wait<WebDriver> getWait() {
        if (wait == null) {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(2));
        }

        return wait;
    }

    public BaseModel(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    protected WebDriver getDriver() {
        return driver;
    }

    protected Actions getAction() {
        if (action == null) {
            action = new Actions(driver);
        }

        return action;
    }
}