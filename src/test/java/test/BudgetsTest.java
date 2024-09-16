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

    @Severity(SeverityLevel.BLOCKER)
    @Story("US_06.001 Create a New Budget")
    @Description("TC_06.001.01 Validate Creation of a New Budget")
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

    @Severity(SeverityLevel.BLOCKER)
    @Story("US_06.004 Input Validation for Budget Amounts")
    @Description("TC_06.004.01 Validate add Valid Amount to Budget")
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

    @Severity(SeverityLevel.BLOCKER)
    @Story("US_06.004 Input Validation for Budget Amounts")
    @Description("TC_06.004.02 Validate add Invalid Amount to Budget")
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
    @Story("US_06.001 Create a New Budget")
    @Description("TC_06.001.02 Validate Budget Page Displays Correct Month and Year")
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
    @Story("US_06.002 Set Periodic Budgeting with Fixed Amounts")
    @Description("TC_06.002.02 Validate Change Budget to Fixed Amount Every Period")
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

    @Severity(SeverityLevel.BLOCKER)
    @Story("US_06.002 Set Periodic Budgeting with Fixed Amounts")
    @Description("TC_06.002.01 Validate Create Budget With add Amount Every Period'")
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
    @Story("US_06.003 Correct for Overspending in Budget")
    @Description("TC_06.003.01 Validate Create Budget With add Amount and Correct for Overspending")
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
