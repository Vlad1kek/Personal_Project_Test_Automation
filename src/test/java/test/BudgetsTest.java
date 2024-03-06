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
}
