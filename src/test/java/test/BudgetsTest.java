package test;

import jdk.jfr.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
import page.HomePage;
import utils.run.BaseTest;
import utils.run.TestUtils;

import java.util.List;

public class BudgetsTest extends BaseTest {
    private static final String NAME_BUDGET = "NewTestBudgets123";

    @Description("Create Budgets FI-T5")
    @Test(priority = 1)
    public void testCreateFirstBudgets() {
        List<String> nameBudget = new HomePage(getDriver())
                .goBudgets()
                .clickCreateBudgets()
                .setName(NAME_BUDGET)
                .submit()
                .getBudgetsNamesText();

        Assert.assertTrue(nameBudget.contains(NAME_BUDGET), "Budget name does not exist");
    }

    @Description("Add budget valid amount FI-T6")
    @Test(priority = 2)
    public void testAddBudgetValidAmount() {
        final String amount = "222.33";

        String actualAmount = new HomePage(getDriver())
                .goBudgets()
                .setBudgetAmount(amount)
                .getBudgetedAmountText();

        Assert.assertEquals(actualAmount, "€" + amount);
    }

    @Description("Add budget invalid amount FI-T7")
    @Test(priority = 3)
    public void testAddBudgetInvalidAmount() {
        final String amount = "abCD";

        String actualAmount = new HomePage(getDriver())
                .goBudgets()
                .setBudgetAmount(amount)
                .getBudgetedAmountText();

        Assert.assertEquals(actualAmount, "€0.00");
    }

    @Description("Budgets correct month and year FI-T8")
    @Test(priority = 2)
    public void testBudgetsCorrectMonthAndYear() {
        final String localDate = TestUtils.getMonthYear();

        String budgetsPageDate = new HomePage(getDriver())
                .goBudgets()
                .getValueBudgetsMonthYear();

        Assert.assertEquals(budgetsPageDate, localDate);
    }

    @Description("Create Budgets with fixed amount FI-T9")
    @Test(priority = 4)
    public void testCreateBudgetsWithFixedAmount() {
        final String amount = "789";

        String calendarAttribute = new HomePage(getDriver())
                .goBudgets()
                .clickCreateBudgets()
                .setName(NAME_BUDGET + "1")
                .setAFixedAmountEveryPeriod()
                .setAutoBudgetAmount(amount)
                .submit()
                .getCalendarCheckTitle(NAME_BUDGET + "1");

        Assert.assertEquals(calendarAttribute, "This budget will be set periodically");
    }

    @Description("Create Budgets add an amount every period FI-T10")
    @Test(priority = 4)
    public void testCreateBudgetsAddAnAmountEveryPeriod() {
        final String amount = "333";

        String calendarAttribute = new HomePage(getDriver())
                .goBudgets()
                .clickCreateBudgets()
                .setName(NAME_BUDGET + "2")
                .setAddAnAmountEveryPeriod()
                .setAutoBudgetAmount(amount)
                .submit()
                .getCalendarCheckTitle(NAME_BUDGET + "2");

        Assert.assertEquals(calendarAttribute, "The budget amount will increase periodically");
    }


    @Description("Create Budgets and correct for overspending FI-T11")
    @Test(priority = 4)
    public void testCreateBudgetsAndCorrectForOverspending() {
        final String amount = "125";

        String calendarAttribute = new HomePage(getDriver())
                .goBudgets()
                .clickCreateBudgets()
                .setName(NAME_BUDGET + "3")
                .setCorrectForOverspending()
                .setAutoBudgetAmount(amount)
                .submit()
                .getCalendarCheckTitle(NAME_BUDGET + "3");

        Assert.assertEquals(calendarAttribute,
                "The budget amount will increase periodically and will correct for overspending");
    }
}
