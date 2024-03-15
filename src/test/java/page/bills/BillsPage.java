package page.bills;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import page.base.BasePage;

import java.time.Duration;
import java.util.List;

public class BillsPage extends BasePage {
    @FindBy(css = "a[class$='btn-success']")
    private WebElement createABill;

    @FindBy(xpath = "//a[starts-with(@href,'http://localhost/bills/show/')]")
    private List<WebElement> billsList;

    @FindBy(className = "introjs-skipbutton")
    private WebElement buttonSkip;

    @FindBy(xpath = "//tbody/tr[4]/td[4]")
    private WebElement expectedMonthlyCosts;

    public BillsPage(WebDriver driver) {
        super(driver);
    }

    public CreateBillsPage clickCreateBill() {
        createABill.click();

        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        if (getDriver().findElements(By.className("introjs-overlay")).size() > 0) {
            buttonSkip.click();
        }

        return new CreateBillsPage(getDriver());
    }

    public List<String> getBillsNamesList() {
        return billsList.stream().map(WebElement::getText).toList();
    }

    public EditBillPage clickPencil(String name) {
        getDriver().findElement(By.xpath("//tr[@data-name='" + name + "']/td[2]/div/a[1]")).click();

        return new EditBillPage(getDriver());
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
}
