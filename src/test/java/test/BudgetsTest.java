package test;

import io.qameta.allure.*;

import org.testng.Assert;
import org.testng.annotations.Test;
import page.HomePage;
import utils.run.BaseTest;
import utils.run.TestUtils;

import java.util.List;

public class BudgetsTest extends BaseTest {
    private static final String NAME_BUDGET = "NewTestBudgets123";

    @Severity(SeverityLevel.CRITICAL)
    @Story("Budgets")
    @Description("Create Budgets FI-T5")
    @Test(priority = 1)
    public void testCreateFirstBudgets() {
        List<String> nameBudget = new HomePage(getDriver())
                .goBudgets()
                .clickCreateBudgets()
                .setName(NAME_BUDGET)
                .submit()
                .getBudgetsNamesText();

        Allure.step("The name '" + NAME_BUDGET + "' of the created Budget is displayed in the Budgets list");
        Assert.assertTrue(nameBudget.contains(NAME_BUDGET),
                "If FAIL: Budget name '" + NAME_BUDGET + "' does not exist");
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("Budgets")
    @Description("Add budget valid amount FI-T6")
    @Test(priority = 2)
    public void testAddBudgetValidAmount() {
        final String amount = "222.33";

        String actualAmount = new HomePage(getDriver())
                .goBudgets()
                .setBudgetAmount(amount)
                .getBudgetedAmountText();

        Allure.step("The entry 'Budgeted: €" + amount + "' will appear above the progress bar");
        Assert.assertEquals(actualAmount, "€" + amount,
                "If FAIL: Budgeted: €'" + amount + "' does NOT equals expected quantity:");
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("Budgets")
    @Description("Add budget invalid amount FI-T7")
    @Test(priority = 3)
    public void testAddBudgetInvalidAmount() {
        final String amount = "abCD";

        String actualAmount = new HomePage(getDriver())
                .goBudgets()
                .setBudgetAmount(amount)
                .getBudgetedAmountText();


        Allure.step("The entry 'Budgeted: €0.00' will appear above the progress bar");
        Assert.assertEquals(actualAmount, "€0.00",
                "If FAIL: NOT equal to expected quantity: Budgeted: €0.00");
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("Budgets")
    @Description("Budgets correct month and year FI-T8")
    @Test(priority = 2)
    public void testBudgetsCorrectMonthAndYear() {
        final String localDate = TestUtils.getMonthYear();

        String budgetsPageDate = new HomePage(getDriver())
                .goBudgets()
                .getValueBudgetsMonthYear();

        Allure.step("At top in the middle month and year will be written correctly according to the locale, " +
                "example: " + localDate);
        Assert.assertEquals(budgetsPageDate, localDate,
                "If FAIL: The date is written incorrectly or does not correspond to local date example:"
                        + localDate);
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("Budgets")
    @Description("Create Budgets with fixed amount FI-T9")
    @Test(priority = 4)
    public void testCreateBudgetsWithFixedAmount() {
        final String expectedMessage = "This budget will be set periodically";

        String calendarAttribute = new HomePage(getDriver())
                .goBudgets()
                .clickCreateBudgets()
                .setName(NAME_BUDGET + "1")
                .setAFixedAmountEveryPeriod()
                .setAutoBudgetAmount("789")
                .submit()
                .getCalendarCheckTitle(NAME_BUDGET + "1");

        Allure.step("To the right of the name budget there is an icon, when you hover it, " +
                "the inscription appears: " + expectedMessage);
        Assert.assertEquals(calendarAttribute, expectedMessage,
                "If FAIL: There is NO icon to the right of the budget name, or when you hover over it, " +
                        "NO or NOT a message appears: " + expectedMessage);
    }

    @Severity(SeverityLevel.NORMAL)
    @Step("Budgets")
    @Description("Create Budgets add an amount every period FI-T10")
    @Test(priority = 4)
    public void testCreateBudgetsAddAnAmountEveryPeriod() {
        final String expectedMessage = "The budget amount will increase periodically";

        String calendarAttribute = new HomePage(getDriver())
                .goBudgets()
                .clickCreateBudgets()
                .setName(NAME_BUDGET + "2")
                .setAddAnAmountEveryPeriod()
                .setAutoBudgetAmount("333")
                .submit()
                .getCalendarCheckTitle(NAME_BUDGET + "2");

        Allure.step("To the right of the name budget there is an icon, when you hover it, " +
                "the inscription appears: " + expectedMessage);
        Assert.assertEquals(calendarAttribute, expectedMessage,
                "If FAIL: There is NO icon to the right of the budget name, or when you hover over it, " +
                        "NO or NOT a message appears: " + expectedMessage);
    }


    @Severity(SeverityLevel.NORMAL)
    @Step("Budgets")
    @Description("Create Budgets and correct for overspending FI-T11")
    @Test(priority = 4)
    public void testCreateBudgetsAndCorrectForOverspending() {
        final String expectedMessage = "The budget amount will increase periodically and will correct for overspending";

        String calendarAttribute = new HomePage(getDriver())
                .goBudgets()
                .clickCreateBudgets()
                .setName(NAME_BUDGET + "3")
                .setCorrectForOverspending()
                .setAutoBudgetAmount("42")
                .submit()
                .getCalendarCheckTitle(NAME_BUDGET + "3");

        Allure.step("To the right of the name budget there is an icon, when you hover it, " +
                "the inscription appears: " + expectedMessage);
        Assert.assertEquals(calendarAttribute, expectedMessage,
                "If FAIL: There is NO icon to the right of the budget name, or when you hover over it, " +
                        "NO or NOT a message appears: " + expectedMessage);
    }
}
