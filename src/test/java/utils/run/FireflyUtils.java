package utils.run;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.log.LogUtils;

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
        BaseUtils.getUrl(driver);
        driver.findElement(By.className("logout-link")).click();
    }

    static void createBank(WebDriver driver) {
        driver.findElement(By.id("ffInput_bank_name")).sendKeys("TestBankName123");
        driver.findElement(By.cssSelector("input[id='bank_balance']")).sendKeys("1000");
        driver.findElement(By.cssSelector("input[type='submit']")).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        driver.findElement(By.cssSelector("a[class$='introjs-skipbutton']")).click();
    }

    static void createToken(WebDriver driver, WebDriverWait wait) {
        try {
            driver.findElement(By.id("option-menu")).click();
            driver.findElement(By.cssSelector("a[href='http://localhost/profile']")).click();
            driver.findElement(By.cssSelector("a[href='#oauth']")).click();
            driver.findElement(By.xpath("//div[@id='oauth']/div/div[3]/div/div/div/div/div/a")).click();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
            driver.findElement(By.id("create-token-name")).sendKeys("token");
            driver.findElement(By.xpath("//div[@id='modal-create-token']/div/div/div[@class='modal-footer']/button[2]")).click();

            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("textarea[readonly]")))).isEnabled();
            token = driver.findElement(By.cssSelector("textarea[readonly]")).getText();
            driver.findElement(By.xpath("//div[@id='modal-access-token']/div/div/div[@class='modal-footer']/button")).click();
        } catch (NoSuchElementException e) {
            LogUtils.logFatal("Element not found during token creation.");
            throw new RuntimeException(e);
        } catch (Exception e) {
            LogUtils.logFatal("Error during token creation: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    static void enableUserRegistration(WebDriver driver) {
        try {
            driver.navigate().to("http://localhost/admin/configuration");
            driver.findElement(By.id("ffInput_single_user_mode")).click();
            driver.findElement(By.className("btn-success")).click();
        } catch (Exception e){
            LogUtils.logException("Enable user registration not success" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    static void createNewUser(WebDriver driver) {
        try {
            driver.findElement(By.cssSelector("a[href$='logout'")).click();
            driver.navigate().to("http://localhost/register");
            driver.findElement(By.name("email")).sendKeys("test2@gmail.com");
            driver.findElement(By.name("password")).sendKeys("^xk!!(SCjLkhjwvu");
            driver.findElement(By.name("password_confirmation")).sendKeys("^xk!!(SCjLkhjwvu");
            submit(driver);
        } catch (Exception e) {
            LogUtils.logException("Create new user not success" + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
