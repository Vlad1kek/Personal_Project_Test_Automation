package test;

import org.testng.Assert;
import org.testng.annotations.Test;

import page.BasePage;
import utils.run.BaseTest;

public class FirstTest extends BaseTest {

    @Test
    public void testFirst() {
        String basePage = new BasePage(getDriver())
                .first()
                .headline();

        Assert.assertEquals(basePage, "Budgets");
    }
}
