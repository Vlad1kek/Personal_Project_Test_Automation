package utils.run;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

/**
 * Utility class for common base operations in Selenium WebDriver automation.
 * <p>
 * The `BaseUtils` class provides methods for:
 * <ul>
 *      <li>Navigating to the base URL of the application under test.</li>
 *      <li>Capturing screenshots during test execution, with the option to save them using the test method and class names for easier identification.</li>
 * </ul>
 *
 * <p><strong>Usage Examples:</strong></p>
 * <li>To navigate to the base URL: <code>BaseUtils.getUrl(driver);</code></li>
 * <li>To capture a screenshot: <code>BaseUtils.captureScreenFile(driver, "testMethod", "TestClass");</code></li>
 */
public class BaseUtils {

    /**
     * Navigates the WebDriver instance to the base URL defined in the project properties.
     *
     * @param driver the WebDriver instance used to navigate to the URL.
     */
    public static void getUrl(WebDriver driver) {
        driver.get(ProjectProperties.url());
    }

    /**
     * Captures a screenshot of the current browser window.
     * <p>
     * The screenshot is saved to the `screenshots/` directory with a filename pattern based on the test class name
     * and the method name provided as parameters. This is useful for debugging or tracking test execution steps.
     *
     * @param driver the WebDriver instance to capture the screenshot from.
     * @param methodName the name of the test method, used to create the screenshot file name.
     * @param className the name of the test class, used to create the screenshot file name.
     * @return the File object of the captured screenshot.
     */
    static File captureScreenFile(WebDriver driver, String methodName, String className) {
        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(file, new File(String.format("screenshots/%s.%s.png", className, methodName)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }
}


