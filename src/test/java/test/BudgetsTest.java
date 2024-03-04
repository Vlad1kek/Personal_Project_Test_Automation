package test;

import jdk.jfr.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
import page.HomePage;
import utils.run.BaseTest;

import java.util.List;

public class BudgetsTest extends BaseTest {

    @Description("Create Budgets FI-T5")
    @Test
    public void testCreateFirstBudgets() {
        final String name = "NewTestBudgets123";

        List<String> nameBudget = new HomePage(getDriver())
                .clickBudgets()
                .clickCreateBudgets()
                .setName(name)
                .submit()
                .getBudgetsNamesText();

        Assert.assertTrue(nameBudget.contains(name), "Budget name does not exist");
    }
}
