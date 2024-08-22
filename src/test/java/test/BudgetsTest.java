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

@Epic("Budget")
public class BudgetsTest extends BaseTest {
    private static final String NAME_BUDGET = "NewTestBudgets123";

    @Severity(SeverityLevel.CRITICAL)
    @Story("TS_06.000 Create budget")
    @Description("06.000.01 Validate creation of a new budget")
    @Test(priority = 1)
    public void testCreateValidBudgets() {
        List<String> nameBudget = new HomePage(getDriver())
                .goBudgets()
                .clickCreateButton()
                .setName(NAME_BUDGET)
                .clickSubmit(new BudgetsDetailsPage(getDriver()))
                .skipTutorial()
                .getNamesList();

        Allure.step(String.format("The name '%s' of the created Budget is displayed in the Budgets list", NAME_BUDGET));
        Assert.assertTrue(nameBudget.contains(NAME_BUDGET),
                "If FAIL: Budget name '" + NAME_BUDGET + "' does not exist");
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("TS_06.001 Change Configure Budget")
    @Description("06.001.01 Validate add valid amount")
    @Test(priority = 2)
    public void testBudgetAddValidAmount() {
        final String amount = "222.33";
        TestUtils.createBudget(this, NAME_BUDGET, true);

        String actualAmount = new HomePage(getDriver())
                .goBudgets()
                .setBudgetAmount(amount)
                .getBudgetedAmountText();

        Allure.step(String.format("The entry 'Budgeted: €%s' will appear above the progress bar", amount));
        Assert.assertEquals(actualAmount, "€" + amount,
                String.format("If FAIL: 'Budgeted: %s' NOT equal to the value entered", actualAmount));
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("TS_06.001 Change Configure Budget")
    @Description("06.001.02 Add invalid amount")
    @Test(priority = 2)
    public void testBudgetAddInvalidAmount() {
        final String amount = "abCD";
        TestUtils.createBudget(this, NAME_BUDGET, true);

        String actualAmount = new HomePage(getDriver())
                .goBudgets()
                .setBudgetAmount(amount)
                .getBudgetedAmountText();

        Allure.step("The entry 'Budgeted: €0.00' will appear above the progress bar");
        Assert.assertEquals(actualAmount, "€0.00",
                String.format("If FAIL: Received value: %s is not equal to expected", actualAmount));
    }

    @Severity(SeverityLevel.MINOR)
    @Story("TS_06.002 Budget page")
    @Description("TC_06.002.01 Correct month and year")
    @Test(priority = 2)
    public void testBudgetsCorrectMonthAndYear() {
        final String localDate = TimeUtils.getMonthYear();

        String budgetsPageDate = new HomePage(getDriver())
                .goBudgets()
                .getValueBudgetsMonthYear();

        Allure.step("At top in the middle month and year will be written correctly according to the locale, " +
                "Example: " + localDate);
        Assert.assertEquals(budgetsPageDate, localDate,
                "If FAIL: The date is written incorrectly or does not correspond to local date:" + budgetsPageDate);
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("TS_06.001 Change Configure Budget")
    @Description("TC_06.001.03 Validate change budget by 'Set a fixed amount every period'")
    @Test(priority = 2)
    public void testChangeBudgetByFixedAmount() {
        final String expectedMessage = "This budget will be set periodically";
        TestUtils.createBudget(this, NAME_BUDGET, true);

        String calendarAttribute = new HomePage(getDriver())
                .goBudgets()
                .clickPencil(NAME_BUDGET)
                .setAFixedAmountEveryPeriod()
                .setAutoBudgetAmount("789")
                .clickSubmit(new BudgetsDetailsPage(getDriver()))
                .getCalendarCheckTitle(NAME_BUDGET);

        Allure.step("To the right of the budget name there is a calendar icon, when you hover over it, " +
                "it the following text appears: " + expectedMessage);
        Assert.assertEquals(calendarAttribute, expectedMessage,
                String.format("If FAIL: received text: '%s' is invalid or could not be found", calendarAttribute));
    }

    @Severity(SeverityLevel.CRITICAL)
    @Story("TS_06.000 Create budget")
    @Description("TC_06.000.02 Validate create budget with 'Add an amount every period'")
    @Test(priority = 2)
    public void testCreateBudgetByAddAmountEveryPeriod() {
        final String expectedMessage = "The budget amount will increase periodically";

        String calendarAttribute = new HomePage(getDriver())
                .goBudgets()
                .clickCreateButton()
                .setName(NAME_BUDGET)
                .setAddAnAmountEveryPeriod()
                .setAutoBudgetAmount("333")
                .clickSubmit(new BudgetsDetailsPage(getDriver()))
                .getCalendarCheckTitle(NAME_BUDGET);

        Allure.step("To the right of the budget name there is a calendar icon, when you hover over it, " +
                "it the following text appears: " + expectedMessage);
        Assert.assertEquals(calendarAttribute, expectedMessage,
                String.format("If FAIL: received text: '%s' is invalid or could not be found", calendarAttribute));
    }


    @Severity(SeverityLevel.CRITICAL)
    @Story("TS_06.000 Create budget")
    @Description("TC_06.000.03 Validate create budgets with 'Add an amount every period and correct for overspending'")
    @Test(priority = 2)
    public void testCreateBudgetsAndCorrectForOverspending() {
        final String expectedMessage = "The budget amount will increase periodically and will correct for overspending";

        String calendarAttribute = new HomePage(getDriver())
                .goBudgets()
                .clickCreateButton()
                .setName(NAME_BUDGET)
                .setCorrectForOverspending()
                .setAutoBudgetAmount("42")
                .clickSubmit(new BudgetsDetailsPage(getDriver()))
                .getCalendarCheckTitle(NAME_BUDGET);

        Allure.step("To the right of the budget name there is a calendar icon, when you hover over it, " +
                "it the following text appears: " + expectedMessage);
        Assert.assertEquals(calendarAttribute, expectedMessage,
                String.format("If FAIL: received text: '%s' is invalid or could not be found", calendarAttribute));
    }
}
