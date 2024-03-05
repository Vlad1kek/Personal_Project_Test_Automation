package test;

import jdk.jfr.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
import page.HomePage;
import utils.run.BaseTest;

import java.util.List;

public class BudgetsTest extends BaseTest {
    static final String NAME_BUDGET = "NewTestBudgets123";

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


    @Description("Add Budget Amount FI-T6")
    @Test(priority = 2)
    public void testAddBudgetAmount() {
        final String amount = "734.33";

        String actualAmount = new HomePage(getDriver())
                .goBudgets()
                .setBudgetAmount(amount)
                .goHomePage()
                .getLeftToSpendNumber();

        Assert.assertEquals(actualAmount, "€" + amount);
    }
}
