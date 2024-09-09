package utils.run;

import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.time.Duration;

/**
 * Utility class for handling various actions related to test automation
 * within the Firefly III application using Selenium WebDriver.
 * <p>
 * This class provides methods for creating bills and budgets, while offering
 * the option to return to the home page after completing the action. It
 * leverages WebDriver interactions and the Allure `@Step` annotation for
 * better reporting in test cases.
 *
 * <p><strong>Usage Examples:</strong></p>
 * <ul>
 *      <li>To create a bill: <code>TestUtils.createBill(baseTest, "Rent", "1000", "2000", true);</code></li>
 *      <li>To create a budget: <code>TestUtils.createBudget(baseTest, "Groceries", true);</code></li>
 * </ul>
 */
public class TestUtils {

    /**
     * Navigates to the home page if the goToHomePage flag is set to true.
     *
     * @param baseTest the test instance providing access to WebDriver.
     * @param goToHomePage flag to indicate whether to navigate to the home page.
     */
    private static void goToHomePage(BaseTest baseTest, boolean goToHomePage) {
        if (goToHomePage) {
            baseTest.getDriver().findElement(By.className("logo-lg")).click();
        }
    }

    /**
     * Creates a new bill in the Firefly III application.
     *
     * @param baseTest the test instance providing access to WebDriver.
     * @param name the name of the bill.
     * @param minAmount the minimum amount for the bill.
     * @param maxAmount the maximum amount for the bill.
     * @param goToHomePage flag to indicate whether to navigate to the home page after the bill is created.
     */
    @Step("Created the new Bill: '{name}' and set Minimum amount = {minAmount}, Maximum amount = {maxAmount}")
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

    /**
     * Creates a new budget in the Firefly III application.
     *
     * @param baseTest the test instance providing access to WebDriver.
     * @param name the name of the budget.
     * @param goToHomePage flag to indicate whether to navigate to the home page after the budget is created.
     */
    @Step("Created the Budget: '{name}'")
    public static void createBudget(BaseTest baseTest, String name, boolean goToHomePage) {
        baseTest.getDriver().findElement(By.cssSelector("li[id='budget-menu")).click();
        baseTest.getDriver().findElement(By.cssSelector("a[class$='btn-success']")).click();
        baseTest.getDriver().findElement(By.name("name")).sendKeys(name);
        baseTest.getDriver().findElement(By.className("btn-success")).click();

        goToHomePage(baseTest, goToHomePage);
    }
}
