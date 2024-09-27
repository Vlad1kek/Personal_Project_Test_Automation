package test;

import io.qameta.allure.*;
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
    private final String billName = getFaker().harryPotter().character();
    private static final String MINIMUM_AMOUNT = "300";
    private static final String MAXIMUM_AMOUNT = "500";

    @Severity(SeverityLevel.BLOCKER)
    @Story("US_05.001 Bill Creation and Recurrence Setup")
    @Description("TC_07.001.01 Create New Bill")
    @Test(priority = 1)
    public void testCreateNewBill() {
        List<String> nameBill = new HomePage(getDriver())
                .goBill()
                .clickCreateButton()
                .skipTutorial()
                .setName(billName)
                .setMinimumAmount(MINIMUM_AMOUNT)
                .setMaximumAmount(MAXIMUM_AMOUNT)
                .clickSubmit(new BillsRulesPage(getDriver()))
                .goBill()
                .skipTutorial()
                .getNamesList();

        Allure.step("The newly created bill should be visible in the Bills List with the correct name");
        Assert.assertTrue(nameBill.contains(billName),
                "If FAIL: Bill is NOT created or NOT available in the list of bills");
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("US_05.001 Bill Creation and Recurrence Setup")
    @Description("TC_07.001.02 Validate The Bill Expected Date 'Repeats Monthly'")
    @Test(priority = 2)
    public void testCheckingTheDateBillsRepeatsMonthlyAndCheckNextExpectedMatch() {
        List<String> currentDateList = TimeUtils.getCurrentDateList();
        TestUtils.createBill(this, billName, MINIMUM_AMOUNT, MAXIMUM_AMOUNT, true);

        List<String> actualMonthlyDate = new HomePage(getDriver())
                .goBill()
                .getNextExpectedMatch(billName);

        Allure.step("The Next Expected Match date should match the current date: " + currentDateList);
        Assert.assertEquals(actualMonthlyDate, currentDateList,
                "If FAIL: The newly created bill is Not set to repeat monthly, or the actual date is " +
                        "incorrect: " + actualMonthlyDate);
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("US_05.001 Bill Creation and Recurrence Setup")
    @Description("TC_07.001.03 Set Bill to 'Repeat Weekly' and Validate Next Expected Match")
    @Test(priority = 2)
    public void testSetBillToRepeatWeeklyAndCheckNextExpectedMatch() {
        List<String> weeklyDatesList = TimeUtils.getWeeklyDatesList();
        TestUtils.createBill(this, billName, MINIMUM_AMOUNT, MAXIMUM_AMOUNT, true);

        List<String> actualWeeklyDate = new HomePage(getDriver())
                .goBill()
                .clickPencil(billName)
                .setRepeatsWeekly()
                .clickSubmit(new BillsDetailsPage(getDriver()))
                .getNextExpectedMatch(billName);

        Allure.step("The Next Expected Match date should match the upcoming weekly date: " + weeklyDatesList);
        Assert.assertEquals(actualWeeklyDate, weeklyDatesList,
                "If FAIL: The newly created bill is Not set to repeat weekly, or the actual date is " +
                        "incorrect: " + actualWeeklyDate);
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("US_05.001 Bill Creation and Recurrence Setup")
    @Description("TC_07.001.04 Set Bill to 'Repeat Daily' and Validate Next Expected Match")
    @Test(priority = 2)
    public void testSetBillToRepeatDailyAndCheckNextExpectedMatch() {
        List<String> dailyDatestList = TimeUtils.getDailyDatesList();
        TestUtils.createBill(this, billName, MINIMUM_AMOUNT, MAXIMUM_AMOUNT, true);

        List<String> actualDailyDate = new HomePage(getDriver())
                .goBill()
                .clickPencil(billName)
                .setRepeatsDaily()
                .clickSubmit(new BillsDetailsPage(getDriver()))
                .getNextExpectedMatch(billName);

        Allure.step("The Next Expected Match date should match the next daily date " + dailyDatestList);
        Assert.assertEquals(actualDailyDate, dailyDatestList,
                "If FAIL: The newly created bill is Not set to repeat daily, or the actual date is " +
                        "incorrect: " + actualDailyDate);
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("US_05.001 Bill Creation and Recurrence Setup")
    @Description("TC_07.001.05 Set Bill to Repeat Yearly and Validate Next Expected Match")
    @Test(priority = 2)
    public void testSetBillToRepeatYearlyAndCheckNextExpectedMatch() {
        List<String> currentDate = TimeUtils.getCurrentDateList();
        TestUtils.createBill(this, billName, MINIMUM_AMOUNT, MAXIMUM_AMOUNT, true);

        List<String> actualYearlyDate = new HomePage(getDriver())
                .goBill()
                .clickPencil(billName)
                .setRepeatsYearly()
                .clickSubmit(new BillsDetailsPage(getDriver()))
                .getNextExpectedMatch(billName);

        Allure.step("The Next Expected Match date should match the next yearly: " + currentDate);
        Assert.assertEquals(actualYearlyDate, currentDate,
        "If FAIL: The newly created bill is Not set to repeat yearly, or the actual date is incorrect: "
                + actualYearlyDate);
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("US_05.001 Bill Creation and Recurrence Setup")
    @Description("TC_07.001.06 Set Bill to Repeat Every Half-Year and Validate Next Expected")
    @Test(priority = 2)
    public void testSetBillToRepeatEveryHalfYearAndCheckNextExpectedMatch() {
        List<String> currentDate = TimeUtils.getCurrentDateList();
        TestUtils.createBill(this, billName, MINIMUM_AMOUNT, MAXIMUM_AMOUNT, true);

        List<String> actualHalfYearDate = new HomePage(getDriver())
                .goBill()
                .clickPencil(billName)
                .setRepeatsHalfYear()
                .clickSubmit(new BillsDetailsPage(getDriver()))
                .getNextExpectedMatch(billName);

        Allure.step("The Next Expected Match date should match the half-yearly recurrence date: " + currentDate);
        Assert.assertEquals(actualHalfYearDate, currentDate,
                "If FAIL: The newly created bill is Not set to repeat every half-year, or the actual date " +
                        "is incorrect: " + actualHalfYearDate);
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("US_05.001 Bill Creation and Recurrence Setup")
    @Description("TC_07.001.07 Set Bill to Repeat Quarterly and Validate Next Expected Match")
    @Test(priority = 2)
    public void testSetBillToRepeatQuarterlyAndCheckNextExpectedMatch() {
        List<String> currentDate = TimeUtils.getCurrentDateList();
        TestUtils.createBill(this, billName, MINIMUM_AMOUNT, MAXIMUM_AMOUNT, true);

        List<String> actualQuarterlyDate = new HomePage(getDriver())
                .goBill()
                .clickPencil(billName)
                .setRepeatsQuarterly()
                .clickSubmit(new BillsDetailsPage(getDriver()))
                .getNextExpectedMatch(billName);

        Allure.step("The Next Expected Match date should match the quarterly recurrence date: " + currentDate);
        Assert.assertEquals(actualQuarterlyDate, currentDate,
                "If FAIL: The newly created bill is Not every three months (quarterly), or the actual date" +
                        "is incorrect: " + actualQuarterlyDate);
    }

    @Severity(SeverityLevel.MINOR)
    @Story("US_05.002 Expected Monthly Costs")
    @Description("TC_07.002.01 Set Bill to Repeat Yearly and Validate Expected Monthly Costs")
    @Test(priority = 2)
    public void testSetBillToRepeatYearlyAndCheckExpectedMonthlyCosts() {
        String minimumAmount = "140";
        String maximumAmount = "580";
        String expectedMonthlyCosts = TimeUtils.getExpectedMonthlyCostsYearly(minimumAmount, maximumAmount);
        TestUtils.createBill(this, billName, MINIMUM_AMOUNT, MAXIMUM_AMOUNT, true);

        String actualMonthlyCosts = new HomePage(getDriver())
                .goBill()
                .clickPencil(billName)
                .setRepeatsYearly()
                .setMaximumAmount(maximumAmount)
                .setMinimumAmount(minimumAmount)
                .clickSubmit(new BillsDetailsPage(getDriver()))
                .getMonthlyCosts();

        Allure.step("'Expected monthly costs' should equal the value calculated: " + expectedMonthlyCosts);
        Assert.assertEquals(actualMonthlyCosts, expectedMonthlyCosts,
                "If FAIL: The displayed 'Expected monthly cost' is not equal the calculated value: "
                        + actualMonthlyCosts);
    }

    @Severity(SeverityLevel.MINOR)
    @Story("US_05.002 Expected Monthly Costs")
    @Description("TC_07.002.02 Set Bill to Repeat Half-Yearly and Validate Expected Monthly Costs")
    @Test(priority = 2, dependsOnMethods = "testSetBillToRepeatYearlyAndCheckExpectedMonthlyCosts")
    public void testSetBillToRepeatHalfYearAndCheckExpectedMonthlyCosts() {
        String minimumAmount = "250";
        String maximumAmount = "270";
        String expectedHalfYear = TimeUtils.getExpectedMonthlyCostsHalfYear(minimumAmount, maximumAmount);

        String actualMonthlyCosts = new HomePage(getDriver())
                .goBill()
                .clickPencil(billName)
                .setRepeatsHalfYear()
                .setMaximumAmount(maximumAmount)
                .setMinimumAmount(minimumAmount)
                .clickSubmit(new BillsDetailsPage(getDriver()))
                .getMonthlyCosts();

        Allure.step("'Expected Monthly Costs' should equal the value calculated: " + expectedHalfYear);
        Assert.assertEquals(actualMonthlyCosts, expectedHalfYear,
                "If FAIL: The displayed 'Expected monthly cost' is not equal the calculated value: "
                        + actualMonthlyCosts);
    }
}
