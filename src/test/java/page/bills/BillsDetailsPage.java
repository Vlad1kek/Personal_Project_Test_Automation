package page.bills;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import page.base.BaseDetailsPage;

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

    public List<String> getNextExpectedMatch(String name) {
        List<WebElement> list = getDriver().findElements(By.xpath("//tr[@data-name='" + name + "']/td[7]"))
                .stream().toList();

        return list.stream().map(WebElement::getText).toList();
    }

    public String getMonthlyCosts() {
        return expectedMonthlyCosts.getText();
    }

    public String getRepeats(String name) {
        return getDriver().findElement(By.xpath("//tr[@data-name='" + name + "']/td[8]")).getText();
    }

    public BillsEditPage clickPencil(String name) {
        getDriver().findElement(By.xpath("//tr[@data-name='" + name + "']/td[2]/div/a[1]/span")).click();

        return new BillsEditPage(getDriver());
    }
}
