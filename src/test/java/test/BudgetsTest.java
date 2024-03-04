package test;

import jdk.jfr.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
import page.HomePage;
import utils.run.BaseTest;

import java.util.List;

public class BudgetsTest extends BaseTest {

    @Description("Create Budgets FI-T5")
    @Test(priority = 1)
    public void testCreateFirstBudgets() {
        final String name = "NewTestBudgets123";

        List<String> nameBudget = new HomePage(getDriver())
                .goBudgets()
                .clickCreateBudgets()
                .setName(name)
                .submit()
                .getBudgetsNamesText();

        Assert.assertTrue(nameBudget.contains(name), "Budget name does not exist");
    }


    @Description("Add Budget Amount FI-T6")
    @Test
    public void testAddBudgetAmount() {
        final String amount = "734";

        String leftToSpend = new HomePage(getDriver())
                .goBudgets()
                .setBudgetAmount(amount)
                .goHomePage()
                .getLeftToSpendNumber();

        System.out.println(leftToSpend);

        Assert.assertEquals(leftToSpend, "€" + amount + ".00");
    }
}
