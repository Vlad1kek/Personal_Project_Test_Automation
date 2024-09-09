package utils.run;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import utils.log.LogUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utility class for managing project properties and WebDriver configurations.
 * <p>
 * This class is designed to load and manage properties from different sources depending on the environment (local or server).
 * It sets up ChromeDriver options based on the properties loaded and provides methods to retrieve these properties.
 * The class is intended to streamline WebDriver initialization and configuration in a CI/CD environment or during local development.
 * <p>
 * Key functionalities include:
 *
 * <ul>
 *      <li>Loading properties from a local `local.properties` file or environment variables when running on a server.</li>
 *      <li>Configuring Chrome options for WebDriver based on the loaded properties.</li>
 *      <li>Providing utility methods for WebDriver initialization and accessing commonly used properties like username, password, and host URL.</li>
 * </ul>
 * <p>
 * The class leverages WebDriverManager for setting up the ChromeDriver.
 *
 * <p><strong>Note:</strong></p>
 * <p>This class is designed to be used in conjunction with Selenium-based tests and assumes the presence of certain environment variables when running in a CI/CD pipeline.
 */
public final class ProjectProperties {

    static final String PREFIX_PROP = "default.";
    private static final String PROP_ADMIN_USERNAME = PREFIX_PROP + "admin.username";
    private static final String PROP_ADMIN_PAS = PREFIX_PROP + "admin.password";
    private static final String ENV_BROWSER_OPTIONS = "BROWSER_OPTIONS";
    private static final String ENV_WEB_OPTIONS = "WEB_OPTIONS";
    private static final String PROP_BROWSER_OPTIONS = PREFIX_PROP + ENV_BROWSER_OPTIONS.toLowerCase();
    private static final String PROP_HOST = PREFIX_PROP + "host";
    private static final String PROP_TOKEN = PREFIX_PROP + "token";

    /** The Properties object that holds all loaded properties. */
    private static final Properties properties = new Properties();

    /** The ChromeOptions object that holds the configuration for ChromeDriver. */
    private static ChromeOptions chromeOptions;

    /** Static block to initialize properties and setup ChromeDriver options. */
    static {
        initProperties();
        setupChromeOptions();
        WebDriverManager.chromedriver().setup();
    }

    /**
     * Initializes properties based on the environment.
     * If running on a server (CI/CD), loads properties from environment variables.
     * Otherwise, loads properties from the local `local.properties` file.
     */
    private static void initProperties() {
        if (isServerRun()) {
            loadServerProperties();
        } else {
            loadLocalProperties();
        }
    }

    /**
     * Loads properties from environment variables when running on a server.
     */
    private static void loadServerProperties() {
        properties.setProperty(PROP_BROWSER_OPTIONS, System.getenv(ENV_BROWSER_OPTIONS));

        String webOptions = System.getenv(ENV_WEB_OPTIONS);
        if (webOptions != null) {
            for (String option : webOptions.split(";")) {
                String[] optionArr = option.split("=");
                if (optionArr.length == 2) {
                    properties.setProperty(PREFIX_PROP + optionArr[0], optionArr[1]);
                } else {
                    LogUtils.logWarning("Invalid web option: " + option);
                }
            }
        }
    }

    /**
     * Loads properties from the `local.properties` file located in the `src/test/resources` directory.
     * Exits the application if the file is not found or cannot be loaded.
     */
    private static void loadLocalProperties() {
        try (InputStream inputStream = BaseUtils.class.getClassLoader().getResourceAsStream("local.properties")) {
            if (inputStream == null) {
                throw new FileNotFoundException("local.properties file not found in src/test/resources/ directory.");
            }
            properties.load(inputStream);
        } catch (IOException e) {
            LogUtils.logError("ERROR: " + e.getMessage());
            LogUtils.logInfo("You need to create it from local.properties.TEMPLATE file.");
            System.exit(1);
        }
    }

    /**
     * Sets up ChromeDriver options based on the loaded properties.
     * Adds command-line arguments to ChromeDriver if they are specified in the properties.
     */
    private static void setupChromeOptions() {
        chromeOptions = new ChromeOptions();
        String options = properties.getProperty(PROP_BROWSER_OPTIONS);
        if (options != null) {
            for (String argument : options.split(";")) {
                chromeOptions.addArguments(argument);
            }
        }
    }

    /**
     * Determines if the application is running on a server (CI/CD environment).
     *
     * @return true if the `CI_RUN` environment variable is set, false otherwise.
     */
    static boolean isServerRun() {
        return System.getenv("CI_RUN") != null;
    }

    /**
     * Creates and returns a new instance of ChromeDriver with the configured options.
     *
     * @return a WebDriver instance configured with ChromeOptions.
     */
    public static WebDriver createDriver() {
        return new ChromeDriver(chromeOptions);
    }

    /**
     * Retrieves the admin username from the properties.
     *
     * @return the admin username as a String.
     */
    public static String getUserName() {
        return properties.getProperty(PROP_ADMIN_USERNAME);
    }

    /**
     * Retrieves the admin password from the properties.
     *
     * @return the admin password as a String.
     */
    public static String getPassword() {
        return properties.getProperty(PROP_ADMIN_PAS);
    }

    /**
     * Retrieves the API token from the properties.
     *
     * @return the API token as a String.
     */
    static String getPropToken() {
        return properties.getProperty(PROP_TOKEN);
    }

    /**
     * Constructs and returns the base URL of the application.
     *
     * @return the base URL as a String, formatted with the host property.
     */
    static String url() {
        return String.format("http://%s",
                properties.getProperty(PROP_HOST));
    }
}
