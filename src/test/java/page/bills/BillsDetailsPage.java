package page.bills;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import page.base.BaseDetailsPage;
import utils.log.LogUtils;

import java.util.List;

public class BillsDetailsPage extends BaseDetailsPage<BillsEditPage, BillsDetailsPage> {
    @FindBy(xpath = "//tbody/tr[4]/td[4]")
    private WebElement expectedMonthlyCosts;

    public BillsDetailsPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected BillsEditPage createEditPage() {
        return new BillsEditPage(getDriver());
    }

    //BugReport
    public List<String> getNextExpectedMatch(String name) {
        if (!getHeadline().contains("Bills")) {
            LogUtils.logException("Expected page Bill Page but was redirected to Home Page");
            goBill();
            LogUtils.logWarning("Redirect to Bill Page completed");
        }

        List<WebElement> list = getDriver().findElements(By.xpath("//tr[@data-name='" + name + "']/td[7]"))
                .stream().toList();

        return list.stream().map(WebElement::getText).toList();
    }

    //BugReport
    public String getMonthlyCosts() {
        //BugReport
        if (!getHeadline().contains("Bills")) {
            LogUtils.logException("Expected page Bill Page but was redirected to Home Page");
            goBill();
            LogUtils.logWarning("Redirect to Bill Page completed");
        }

        return expectedMonthlyCosts.getText();
    }

    public String getRepeats(String name) {
        return getDriver().findElement(By.xpath("//tr[@data-name='" + name + "']/td[8]")).getText();
    }

    public BillsEditPage clickPencil(String name) {
        getDriver().findElement(By.xpath(   "//tr[@data-name='" + name + "']/td[2]/div/a[1]/span")).click();

        return new BillsEditPage(getDriver());
    }
}
