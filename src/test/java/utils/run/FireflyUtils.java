package utils.run;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

public class FireflyUtils {
    public static String token;

    static void login(WebDriver driver) {
        namePassword(driver);
        submit(driver);
    }

    static void firstLogin(WebDriver driver) {
        namePassword(driver);
        driver.findElement(By.name("password_confirmation")).sendKeys(ProjectProperties.getPassword());
        submit(driver);
    }

    static void namePassword(WebDriver driver) {
        driver.findElement(By.name("email")).sendKeys(ProjectProperties.getUserName());
        driver.findElement(By.name("password")).sendKeys(ProjectProperties.getPassword());
    }

    static void submit(WebDriver driver) {
        driver.findElement(By.cssSelector("button[type='submit']")).click();
    }

    static void logout(WebDriver driver) {
        BaseUtils.get(driver);

        driver.findElement(By.className("logout-link")).click();
    }

    static void createBank(WebDriver driver) {
        driver.findElement(By.id("ffInput_bank_name")).sendKeys("TestBankName123");
        driver.findElement(By.cssSelector("input[id='bank_balance']")).sendKeys("1000");
        driver.findElement(By.cssSelector("input[type='submit']")).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        driver.findElement(By.cssSelector("a[class$='introjs-skipbutton']")).click();
    }

    static void createToken(WebDriver driver) {
        driver.findElement(By.id("option-menu")).click();
        driver.findElement(By.cssSelector("a[href='http://localhost/profile']")).click();
        driver.findElement(By.cssSelector("a[href='#oauth']")).click();
        driver.findElement(By.xpath("//div[@id='oauth']/div/div[3]/div/div/div/div/div/a")).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        driver.findElement(By.id("create-token-name")).sendKeys("token");
        driver.findElement(By.xpath("//div[@id='modal-create-token']/div/div/div[@class='modal-footer']/button[2]")).click();
        token = driver.findElement(By.cssSelector("textarea[readonly]")).getText();
        driver.findElement(By.xpath("//div[@id='modal-access-token']/div/div/div[@class='modal-footer']/button")).click();
    }
}
