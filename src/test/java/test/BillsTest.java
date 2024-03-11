package test;

import org.testng.Assert;
import org.testng.annotations.Test;
import page.HomePage;
import utils.run.BaseTest;

import java.util.List;

public class BillsTest extends BaseTest {
    private static final String BILLS_NAME = "NewTestBills48";

    @Test
    public void CreateNewBills() {
        List<String> nameBill = new HomePage(getDriver())
                .goBill()
                .clickCreateBill()
                .setName(BILLS_NAME)
                .setMinimumAmount(3)
                .setMaximumAmount(5)
                .submit()
                .getBillsNamesList();
        System.out.println(nameBill);

        Assert.assertTrue(nameBill.contains(BILLS_NAME));
    }
}
