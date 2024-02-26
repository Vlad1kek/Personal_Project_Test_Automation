package utils.run;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FireflyUtils {

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
}
