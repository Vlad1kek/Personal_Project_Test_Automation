package utils.run;

import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.time.Duration;

public class TestUtils {

    private static void goToHomePage(BaseTest baseTest, boolean goToHomePage) {
        if (goToHomePage) {
            baseTest.getDriver().findElement(By.className("logo-lg")).click();
        }
    }

    @Step("Create the Bill: '{name}' and set minAmount={minAmount}, maxAmount={maxAmount}")
    public static void createBill(BaseTest baseTest, String name, String minAmount, String maxAmount, boolean goToHomePage) {
        baseTest.getDriver().findElement(By.cssSelector("a[href$='/bills']")).click();
        baseTest.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        baseTest.getDriver().findElement(By.cssSelector("a[class$='btn-success']")).click();
        baseTest.getDriver().findElement(By.name("name")).sendKeys(name);
        baseTest.getDriver().findElement(By.name("amount_min")).sendKeys(minAmount);
        baseTest.getDriver().findElement(By.name("amount_max")).sendKeys(maxAmount);
        baseTest.getDriver().findElement(By.className("btn-success")).click();

        goToHomePage(baseTest, goToHomePage);
    }

    @Step("Create the Budget: '{name}'")
    public static void createBudget(BaseTest baseTest, String name, boolean goToHomePage) {
        baseTest.getDriver().findElement(By.cssSelector("li[id='budget-menu")).click();
        baseTest.getDriver().findElement(By.cssSelector("a[class$='btn-success']")).click();
        baseTest.getDriver().findElement(By.name("name")).sendKeys(name);
        baseTest.getDriver().findElement(By.className("btn-success")).click();

        goToHomePage(baseTest, goToHomePage);
    }
}
