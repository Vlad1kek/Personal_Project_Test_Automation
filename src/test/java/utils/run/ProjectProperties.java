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

public final class ProjectProperties {
    static final String PREFIX_PROP = "default.";
    private static final String PROP_ADMIN_USERNAME = PREFIX_PROP + "admin.username";
    private static final String PROP_ADMIN_PAS = PREFIX_PROP + "admin.password";
    private static final String ENV_BROWSER_OPTIONS = "BROWSER_OPTIONS";
    private static final String ENV_WEB_OPTIONS = "WEB_OPTIONS";
    private static final String PROP_BROWSER_OPTIONS = PREFIX_PROP + ENV_BROWSER_OPTIONS.toLowerCase();
    private static final String PROP_HOST = PREFIX_PROP + "host";
    private static final String PROP_TOKEN = PREFIX_PROP + "token";

    private static final Properties properties = new Properties();
    private static ChromeOptions chromeOptions;

    static {
        initProperties();
        setupChromeOptions();
        WebDriverManager.chromedriver().setup();
    }

    private static void initProperties() {
        if (isServerRun()) {
            loadServerProperties();
        } else {
            loadLocalProperties();
        }
    }

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

    private static void setupChromeOptions() {
        chromeOptions = new ChromeOptions();
        String options = properties.getProperty(PROP_BROWSER_OPTIONS);
        if (options != null) {
            for (String argument : options.split(";")) {
                chromeOptions.addArguments(argument);
            }
        }
    }

    static boolean isServerRun() {
        return System.getenv("CI_RUN") != null;
    }

    public static WebDriver createDriver() {
        return new ChromeDriver(chromeOptions);
    }

    public static String getUserName() {
        return properties.getProperty(PROP_ADMIN_USERNAME);
    }

    public static String getPassword() {
        return properties.getProperty(PROP_ADMIN_PAS);
    }

    static String getPropToken() {
        return properties.getProperty(PROP_TOKEN);
    }

    static String url() {
        return String.format("http://%s",
                properties.getProperty(PROP_HOST));
    }
}
