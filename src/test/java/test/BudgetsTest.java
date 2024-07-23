package test;

import io.qameta.allure.*;

import org.testng.Assert;
import org.testng.annotations.Test;
import page.HomePage;
import page.budgets.BudgetsDetailsPage;
import utils.run.BaseTest;
import utils.run.TestUtils;
import utils.run.TimeUtils;

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
                .clickCreateButton()
                .setName(NAME_BUDGET)
                .clickStoreNew(new BudgetsDetailsPage(getDriver()))
                .skipTutorial()
                .getNamesList();

        System.out.println(nameBudget);
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
        TestUtils.createBudget(this, NAME_BUDGET, true);

        String actualAmount = new HomePage(getDriver())
                .goBudgets()
                .setBudgetAmount(amount)
                .getBudgetedAmountText();

        Allure.step("The entry 'Budgeted: €" + amount + "' will appear above the progress bar");
        Assert.assertEquals(actualAmount, "€" + amount,
                "If FAIL: Budgeted: €'" + actualAmount + "' does NOT equals expected quantity:");
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("Budgets")
    @Description("Add budget invalid amount FI-T7")
    @Test(priority = 2)
    public void testAddBudgetInvalidAmount() {
        final String amount = "abCD";
        TestUtils.createBudget(this, NAME_BUDGET, true);

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
        final String localDate = TimeUtils.getMonthYear();
        TestUtils.createBudget(this, NAME_BUDGET, true);

        String budgetsPageDate = new HomePage(getDriver())
                .goBudgets()
                .getValueBudgetsMonthYear();

        Allure.step("At top in the middle month and year will be written correctly according to the locale, " +
                "example: " + localDate);
        Assert.assertEquals(budgetsPageDate, localDate,
                "If FAIL: The date is written incorrectly or does not correspond to local date example:"
                        + budgetsPageDate);
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("Budgets")
    @Description("Change budgets to a fixed amount FI-T9")
    @Test(priority = 2)
    public void testCreateBudgetsWithFixedAmount() {
        final String expectedMessage = "This budget will be set periodically";
        TestUtils.createBudget(this, NAME_BUDGET, true);

        String calendarAttribute = new HomePage(getDriver())
                .goBudgets()
                .clickPencil(NAME_BUDGET)
                .setAFixedAmountEveryPeriod()
                .setAutoBudgetAmount("789")
                .clickStoreNew(new BudgetsDetailsPage(getDriver()))
                .getCalendarCheckTitle(NAME_BUDGET);

        Allure.step("To the right of the name budget there is an icon, when you hover it, " +
                "the inscription appears: " + calendarAttribute);
        Assert.assertEquals(calendarAttribute, expectedMessage,
                "If FAIL: There is NO icon to the right of the budget name, or when you hover over it, " +
                        "NO or NOT a message appears: " + expectedMessage);
    }

    @Severity(SeverityLevel.NORMAL)
    @Step("Budgets")
    @Description("Create Budgets add an amount every period FI-T10")
    @Test(priority = 2)
    public void testCreateBudgetsAddAnAmountEveryPeriod() {
        final String expectedMessage = "The budget amount will increase periodically";

        String calendarAttribute = new HomePage(getDriver())
                .goBudgets()
                .clickCreateButton()
                .setName(NAME_BUDGET)
                .setAddAnAmountEveryPeriod()
                .setAutoBudgetAmount("333")
                .clickStoreNew(new BudgetsDetailsPage(getDriver()))
                .getCalendarCheckTitle(NAME_BUDGET);

        Allure.step("To the right of the name budget there is an icon, when you hover it, " +
                "the inscription appears: " + expectedMessage);
        Assert.assertEquals(calendarAttribute, expectedMessage,
                "If FAIL: There is NO icon to the right of the budget name, or when you hover over it, " +
                        "NO or NOT a message appears: " + expectedMessage);
    }


    @Severity(SeverityLevel.NORMAL)
    @Step("Budgets")
    @Description("Create Budgets and correct for overspending FI-T11")
    @Test(priority = 2)
    public void testCreateBudgetsAndCorrectForOverspending() {
        final String expectedMessage = "The budget amount will increase periodically and will correct for overspending";

        String calendarAttribute = new HomePage(getDriver())
                .goBudgets()
                .clickCreateButton()
                .setName(NAME_BUDGET)
                .setCorrectForOverspending()
                .setAutoBudgetAmount("42")
                .clickStoreNew(new BudgetsDetailsPage(getDriver()))
                .getCalendarCheckTitle(NAME_BUDGET);

        Allure.step("To the right of the name budget there is an icon, when you hover it, " +
                "the inscription appears: " + expectedMessage);
        Assert.assertEquals(calendarAttribute, expectedMessage,
                "If FAIL: There is NO icon to the right of the budget name, or when you hover over it, " +
                        "NO or NOT a message appears: " + expectedMessage);
    }
}
