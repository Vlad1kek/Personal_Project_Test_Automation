package page.bills;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import page.base.BasePage;

import java.util.List;

public class BillsPage extends BasePage {
    @FindBy(css = "a[class$='btn-success']")
    private WebElement createABill;

    @FindBy(xpath = "//a[starts-with(@href,'http://localhost/bills/show/')]")
    private List<WebElement> billsList;

    public BillsPage(WebDriver driver) {
        super(driver);
    }

    public CreateBillsPage clickCreateBill() {
        createABill.click();

        return new CreateBillsPage(getDriver());
    }

    public List<String> getBillsNamesList() {
        return billsList.stream().map(WebElement::getText).toList();
    }
}
