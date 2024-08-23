package test;

import io.qameta.allure.Epic;
import jdk.jfr.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
import page.HomePage;
import page.bills.BillsDetailsPage;
import page.bills.BillsRulesPage;
import utils.run.BaseTest;
import utils.run.TestUtils;
import utils.run.TimeUtils;

import java.util.List;

@Epic("Bill")
public class BillsTest extends BaseTest {
    private static final String BILLS_NAME = "NewTestBills22";
    private static final String MINIMUM_AMOUNT = "300";
    private static final String MAXIMUM_AMOUNT = "500";

    @Description("Create new bills FI-T12")
    @Test(priority = 1)
    public void testCreateNewBills() {
        List<String> nameBill = new HomePage(getDriver())
                .goBill()
                .clickCreateButton()
                .skipTutorial()
                .setName(BILLS_NAME)
                .setMinimumAmount(MINIMUM_AMOUNT)
                .setMaximumAmount(MAXIMUM_AMOUNT)
                .clickSubmit(new BillsRulesPage(getDriver()))
                .goBill()
                .skipTutorial()
                .getNamesList();

        Assert.assertTrue(nameBill.contains(BILLS_NAME));
    }

    @Description("Checking the date Bills repeats monthly FI-T13")
    @Test(priority = 2)
    public void testCheckingTheDateBillsRepeatsMonthlyAndCheckNextExpectedMatch() {
        List<String> currentDate = TimeUtils.getCurrentDateList();
        TestUtils.createBill(this, BILLS_NAME, MINIMUM_AMOUNT, MAXIMUM_AMOUNT, true);

        List<String> nextExpectedMatch = new HomePage(getDriver())
                .goBill()
                .getNextExpectedMatch(BILLS_NAME);

        Assert.assertEquals(nextExpectedMatch, currentDate);
    }

    @Description("Set Bill to repeat weekly And check next expected match FI-T14")
    @Test(priority = 2)
    public void testSetBillToRepeatWeeklyAndCheckNextExpectedMatch() {
        List<String> weeklyDatesList = TimeUtils.getWeeklyDatesList();
        TestUtils.createBill(this, BILLS_NAME, MINIMUM_AMOUNT, MAXIMUM_AMOUNT, true);

        List<String> nextExpectedMatch = new HomePage(getDriver())
                .goBill()
                .clickPencil(BILLS_NAME)
                .setRepeatsWeekly()
                .clickSubmit(new BillsDetailsPage(getDriver()))
                .getNextExpectedMatch(BILLS_NAME);

        Assert.assertEquals(nextExpectedMatch, weeklyDatesList);
    }

    @Description("Set Bill to repeat daily And check next expected match FI-T15")
    @Test(priority = 2)
    public void testSetBillToRepeatDailyAndCheckNextExpectedMatch() {
        List<String> dailyDatestList = TimeUtils.getDailyDatesList();
        TestUtils.createBill(this, BILLS_NAME, MINIMUM_AMOUNT, MAXIMUM_AMOUNT, true);

        List<String> nextExpectedMatch = new HomePage(getDriver())
                .goBill()
                .clickPencil(BILLS_NAME)
                .setRepeatsDaily()
                .clickSubmit(new BillsDetailsPage(getDriver()))
                .getNextExpectedMatch(BILLS_NAME);

        Assert.assertEquals(nextExpectedMatch, dailyDatestList);
    }

    @Description("Set Bill to repeat yearly And check next expected match FI-T16")
    @Test(priority = 2)
    public void testSetBillToRepeatYearlyAndCheckNextExpectedMatch() {
        List<String> currentDate = TimeUtils.getCurrentDateList();
        TestUtils.createBill(this, BILLS_NAME, MINIMUM_AMOUNT, MAXIMUM_AMOUNT, true);

        List<String> nextExpectedMatch = new HomePage(getDriver())
                .goBill()
                .clickPencil(BILLS_NAME)
                .setRepeatsYearly()
                .clickSubmit(new BillsDetailsPage(getDriver()))
                .getNextExpectedMatch(BILLS_NAME);

        Assert.assertEquals(nextExpectedMatch, currentDate);
    }

    @Description("Set Bill to repeat every half-year And check next expected match FI-T17")
    @Test(priority = 2)
    public void testSetBillToRepeatEveryHalfYearAndCheckNextExpectedMatch() {
        List<String> currentDate = TimeUtils.getCurrentDateList();
        TestUtils.createBill(this, BILLS_NAME, MINIMUM_AMOUNT, MAXIMUM_AMOUNT, true);

        List<String> nextExpectedMatch = new HomePage(getDriver())
                .goBill()
                .clickPencil(BILLS_NAME)
                .setRepeatsHalfYear()
                .clickSubmit(new BillsDetailsPage(getDriver()))
                .getNextExpectedMatch(BILLS_NAME);

        Assert.assertEquals(nextExpectedMatch, currentDate);
    }

    @Description("Set Bill to repeat quarterly And check next expected match FI-T18")
    @Test(priority = 2)
    public void testSetBillToRepeatQuarterlyAndCheckNextExpectedMatch() {
        List<String> currentDate = TimeUtils.getCurrentDateList();
        TestUtils.createBill(this, BILLS_NAME, MINIMUM_AMOUNT, MAXIMUM_AMOUNT, true);

        List<String> nextExpectedMatch = new HomePage(getDriver())
                .goBill()
                .clickPencil(BILLS_NAME)
                .setRepeatsQuarterly()
                .clickSubmit(new BillsDetailsPage(getDriver()))
                .getNextExpectedMatch(BILLS_NAME);

        Assert.assertEquals(nextExpectedMatch, currentDate);
    }

    @Description("Set Bill to repeat yearly And check expected monthly costs FI-T19")
    @Test(priority = 2)
    public void testSetBillToRepeatYearlyAndCheckExpectedMonthlyCosts() {
        String expectedMonthlyCosts = TimeUtils.getExpectedMonthlyCostsYearly(MINIMUM_AMOUNT, MAXIMUM_AMOUNT);
        TestUtils.createBill(this, BILLS_NAME, MINIMUM_AMOUNT, MAXIMUM_AMOUNT, true);

        String monthlyCosts = new HomePage(getDriver())
                .goBill()
                .clickPencil(BILLS_NAME)
                .setRepeatsYearly()
                .clickSubmit(new BillsDetailsPage(getDriver()))
                .getMonthlyCosts();

        Assert.assertEquals(monthlyCosts, expectedMonthlyCosts);
    }

    @Description("Set Bill to repeat half-year And check expected monthly costs FI-T20")
    @Test(priority = 2, dependsOnMethods = "testSetBillToRepeatYearlyAndCheckExpectedMonthlyCosts")
    public void testSetBillToRepeatHalfYearAndCheckExpectedMonthlyCosts() {
        String expectedHalfYear = TimeUtils.getExpectedMonthlyCostsHalfYear(MINIMUM_AMOUNT, MAXIMUM_AMOUNT);

        String monthlyCosts = new HomePage(getDriver())
                .goBill()
                .clickPencil(BILLS_NAME)
                .setRepeatsHalfYear()
                .clickSubmit(new BillsDetailsPage(getDriver()))
                .getMonthlyCosts();

        Assert.assertEquals(monthlyCosts, expectedHalfYear);
    }
}
