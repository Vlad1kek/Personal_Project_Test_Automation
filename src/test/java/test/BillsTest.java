package test;

import jdk.jfr.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
import page.HomePage;
import utils.run.BaseTest;
import utils.run.TestUtils;

import java.util.List;
import java.util.ListIterator;

public class BillsTest extends BaseTest {
    private static final String BILLS_NAME = "NewTestBills22";

    @Description("Create new bills FI-T12")
    @Test(priority = 1)
    public void testCreateNewBills() {
        List<String> nameBill = new HomePage(getDriver())
                .goBill()
                .clickCreateBill()
                .setName(BILLS_NAME)
                .setMinimumAmount(300)
                .setMaximumAmount(500)
                .submit()
                .goBill()
                .getBillsNamesList();

        Assert.assertTrue(nameBill.contains(BILLS_NAME));
    }

    @Description("Checking the date Bills repeats monthly FI-T13")
    @Test(priority = 2)
    public void testCheckingTheDateBillsRepeatsMonthlyAndCheckNextExpectedMatch() {
        List<String> currentDate = TestUtils.getCurrentDateList();

        List<String> nextExpectedMatch = new HomePage(getDriver())
                .goBill()
                .getNextExpectedMatch(BILLS_NAME);

        Assert.assertEquals(nextExpectedMatch, currentDate);
    }

    @Description("Set Bill to repeat weekly FI-T14")
    @Test(priority = 3)
    public void testSetBillToRepeatWeeklyAndCheckNextExpectedMatch() {
        List<String> weeklyDatesList = TestUtils.getWeeklyDatesList();

        List<String> nextExpectedMatch = new HomePage(getDriver())
                .goBill()
                .clickPencil(BILLS_NAME)
                .setRepeatsWeekly()
                .clickSubmit()
                .getNextExpectedMatch(BILLS_NAME);

        Assert.assertEquals(nextExpectedMatch, weeklyDatesList);
    }

    @Description("Set Bill to repeat daily FI-T15")
    @Test(priority = 3)
    public void testSetBillToRepeatDailyAndCheckNextExpectedMatch() {
        List<String> dailyDatestList = TestUtils.getDailyDatesList();

        List<String> nextExpectedMatch = new HomePage(getDriver())
                .goBill()
                .clickPencil(BILLS_NAME)
                .setRepeatsDaily()
                .clickSubmit()
                .getNextExpectedMatch(BILLS_NAME);

        Assert.assertEquals(nextExpectedMatch, dailyDatestList);
    }

    @Description("Set Bill to repeat yearly FI-T16")
    @Test
    public void testSetBillToRepeatYearlyAndCheckNextExpectedMatch() {
        List<String> currentDate = TestUtils.getCurrentDateList();

        List<String> nextExpectedMatch = new HomePage(getDriver())
                .goBill()
                .clickPencil(BILLS_NAME)
                .setRepeatsYearly()
                .clickSubmit()
                .getNextExpectedMatch(BILLS_NAME);

        Assert.assertEquals(nextExpectedMatch, currentDate);
    }
}
