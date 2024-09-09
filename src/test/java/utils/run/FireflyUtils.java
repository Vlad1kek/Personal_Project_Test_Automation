package utils.run;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.log.LogUtils;

import java.time.Duration;

/**
 * Utility class for automating common actions within the Firefly III application using Selenium WebDriver.
 * <p>
 * This class provides static methods to perform various tasks, including:
 *
 * <ul>
 *     <li>Logging in and out of the application.</li>
 *     <li>Creating and managing bank accounts.</li>
 *     <li>Generating API tokens.</li>
 *     <li>Enabling user registration.</li>
 *     <li>Creating new user accounts.</li>
 * </ul>
 *
 * The methods within this class use WebDriver interactions combined with explicit waits to ensure reliable execution.
 * Configurations such as URLs and credentials are dynamically retrieved from the `ProjectProperties` class.
 *
 * <p><strong>Usage Examples:</strong></p>
 * <ul>
 *     <li><code>FireflyUtils.login(driver);</code> - Logs in to the application.</li>
 *     <li><code>FireflyUtils.createBank(driver, wait);</code> - Creates a new bank account.</li>
 *     <li><code>FireflyUtils.createToken(driver, wait);</code> - Generates an API token.</li>
 * </ul>
 *
 * <p><strong>Note:</strong></p>
 * <p>This class assumes that WebDriver setup and project configuration, such as driver initialization and property management, are handled separately in the project.</p>
 */
public class FireflyUtils {

    /**
     * The API token used for authenticating requests.
     * <p>
     * This static variable is set during the token creation process and can be used for making authenticated API calls.
     */
    public static String token;

    /**
     * Logs in to the Firefly III application using stored credentials.
     *
     * @param driver the WebDriver instance used to perform browser interactions
     */
    public static void login(WebDriver driver) {
        enterCredentials(driver);
        submitForm(driver);
    }

    /**
     * Handles the first login process by entering credentials and confirming the password.
     *
     * @param driver the WebDriver instance used to perform browser interactions
     */
    public static void firstLogin(WebDriver driver) {
        enterCredentials(driver);
        driver.findElement(By.name("password_confirmation")).sendKeys(ProjectProperties.getPassword());
        submitForm(driver);
    }

    /**
     * Enters the user's email and password into the login form.
     *
     * @param driver the WebDriver instance used to perform browser interactions
     */
    private static void enterCredentials(WebDriver driver) {
        driver.findElement(By.name("email")).sendKeys(ProjectProperties.getUserName());
        driver.findElement(By.name("password")).sendKeys(ProjectProperties.getPassword());
    }

    /**
     * Submits a form by clicking the submit button.
     *
     * @param driver the WebDriver instance used to perform browser interactions
     */
    private static void submitForm(WebDriver driver) {
        driver.findElement(By.cssSelector("button[type='submit']")).click();
    }

    /**
     * Logs out from the Firefly III application.
     *
     * @param driver the WebDriver instance used to perform browser interactions
     */
    public static void logout(WebDriver driver) {
        navigateToBaseUrl(driver);
        clickElement(driver, By.className("logout-link"));
    }

    /**
     * Creates a new bank account entry within the Firefly III application.
     *
     * @param driver the WebDriver instance used to perform browser interactions
     */
    public static void createBank(WebDriver driver) {
        try {
            enterBankDetails(driver);
            clickElement(driver, By.cssSelector("input[type='submit']"));
            waitForAndClickSkipIntro(driver);
        } catch (WebDriverException e) {
            LogUtils.logException("Failed to create bank: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Enters the bank name and balance into the bank creation form.
     *
     * @param driver the WebDriver instance used to perform browser interactions
     */
    private static void enterBankDetails(WebDriver driver) {
        driver.findElement(By.id("ffInput_bank_name")).sendKeys("TestBankName123");
        driver.findElement(By.cssSelector("input[id='bank_balance']")).sendKeys("0");
    }

    /**
     * Waits for the 'Skip Intro' button to become clickable and then clicks it.
     *
     * @param driver the WebDriver instance used to perform browser interactions
     */
    private static void waitForAndClickSkipIntro(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        clickElement(driver, By.cssSelector("a[class$='introjs-skipbutton']"));
    }

    /**
     * Creates a new API token in the Firefly III application and stores it in the static `token` variable.
     *
     * @param driver the WebDriver instance used to perform browser interactions
     * @param wait   the WebDriverWait instance used to manage wait conditions
     */
    public static void createToken(WebDriver driver, WebDriverWait wait) {
        try {
            navigateToProfilePage(driver);
            initiateTokenCreation(driver);
            inputTokenDetails(driver);
            extractAndSaveToken(driver, wait);
        } catch (NoSuchElementException e) {
            LogUtils.logFatal("Element not found during token creation.");
            throw new RuntimeException(e);
        } catch (WebDriverException e) {
            LogUtils.logFatal("Error during token creation: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Navigates to the user's profile page to begin the process of creating an API token.
     *
     * @param driver the WebDriver instance used to perform browser interactions
     */
    private static void navigateToProfilePage(WebDriver driver) {
        clickElement(driver, By.id("option-menu"));
        clickElement(driver, By.cssSelector("a[href$='profile']"));
        clickElement(driver, By.cssSelector("a[href='#oauth']"));
    }

    /**
     * Initiates the token creation process by clicking the appropriate buttons.
     *
     * @param driver the WebDriver instance used to perform browser interactions
     */
    private static void initiateTokenCreation(WebDriver driver) {
        clickElement(driver, By.xpath("//div[@id='oauth']/div/div[3]/div/div/div/div/div/a"));
    }

    /**
     * Enters the token name and submits the token creation form.
     *
     * @param driver the WebDriver instance used to perform browser interactions
     */
    private static void inputTokenDetails(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        driver.findElement(By.id("create-token-name")).sendKeys("token");
        clickElement(driver, By.xpath("//div[@id='modal-create-token']/div/div/div[@class='modal-footer']/button[2]"));
    }

    /**
     * Extracts the newly created API token from the textarea and stores it.
     *
     * @param driver the WebDriver instance used to perform browser interactions
     * @param wait   the WebDriverWait instance used to manage wait conditions
     */
    private static void extractAndSaveToken(WebDriver driver, WebDriverWait wait) {
        WebElement tokenElement = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("textarea[readonly]"))));
        token = tokenElement.getText();
        clickElement(driver, By.xpath("//div[@id='modal-access-token']/div/div/div[@class='modal-footer']/button"));
    }

    /**
     * Enables user registration by navigating to the admin configuration page and toggling the appropriate setting.
     *
     * @param driver the WebDriver instance used to perform browser interactions
     */
    public static void enableUserRegistration(WebDriver driver) {
        try {
            navigateToUrl(driver, "/admin/configuration");
            clickElement(driver, By.id("ffInput_single_user_mode"));
            clickElement(driver, By.className("btn-success"));
        } catch (WebDriverException e) {
            LogUtils.logException("Failed to enable user registration: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a new user account in the Firefly III application.
     *
     * @param driver the WebDriver instance used to perform browser interactions
     */
    public static void createNewUser(WebDriver driver) {
        try {
            logout(driver);
            navigateToUrl(driver, "/register");
            enterNewUserDetails(driver);
            submitForm(driver);
        } catch (WebDriverException e) {
            LogUtils.logException("Failed to create new user: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Enters the email, password, and password confirmation to create a new user.
     *
     * @param driver the WebDriver instance used to perform browser interactions
     */
    private static void enterNewUserDetails(WebDriver driver) {
        driver.findElement(By.name("email")).sendKeys("test2@gmail.com");
        driver.findElement(By.name("password")).sendKeys("^xk!!(SCjLkhjwvu");
        driver.findElement(By.name("password_confirmation")).sendKeys("^xk!!(SCjLkhjwvu");
    }

    /**
     * Navigates to the account registration page.
     *
     * @param driver the WebDriver instance used to perform browser interactions
     */
    public static void moveToRegisterAccount(WebDriver driver) {
        navigateToUrl(driver, "/register");
    }

    /**
     * Navigates to the base URL of the application.
     *
     * @param driver the WebDriver instance used to perform browser interactions
     */
    private static void navigateToBaseUrl(WebDriver driver) {
        driver.navigate().to(ProjectProperties.url());
    }

    /**
     * Navigates to a specific URL path within the application.
     *
     * @param driver the WebDriver instance used to perform browser interactions
     * @param path   the relative path to navigate to
     */
    private static void navigateToUrl(WebDriver driver, String path) {
        driver.navigate().to(ProjectProperties.url() + path);
    }

    /**
     * Clicks on a web element located by a specific locator.
     *
     * @param driver  the WebDriver instance used to perform browser interactions
     * @param locator the By locator used to find the element
     */
    private static void clickElement(WebDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }
}
