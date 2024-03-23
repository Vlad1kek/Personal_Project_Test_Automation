package utils.run;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import utils.log.LogUtils;

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

    private static Properties properties;

    private static void initProperties() {
        if (properties == null) {
            properties = new Properties();
            if (isServerRun()) {
                properties.setProperty(PROP_BROWSER_OPTIONS, System.getenv(ENV_BROWSER_OPTIONS));

                if (System.getenv(ENV_WEB_OPTIONS) != null) {
                    for (String option : System.getenv(ENV_WEB_OPTIONS).split(";")) {
                        String[] optionArr = option.split("=");
                        properties.setProperty(PREFIX_PROP + optionArr[0], optionArr[1]);
                    }
                }
            } else {
                try {
                    InputStream inputStream = BaseUtils.class.getClassLoader().getResourceAsStream("local.properties");
                    properties.load(inputStream);
                } catch (IOException ignore) {
                    LogUtils.logError("ERROR: The \u001B[31mlocal.properties\u001B[0m file not found in src/test/resources/ directory.");
                    LogUtils.logInfo("You need to create it from local.properties.TEMPLATE file.");
                    System.exit(1);
                }
            }
        }
    }

    private static final ChromeOptions chromeOptions;

    static {
        initProperties();

        chromeOptions = new ChromeOptions();
        String options = properties.getProperty(PROP_BROWSER_OPTIONS);
        if (options != null) {
            for (String argument : options.split(";")) {
                chromeOptions.addArguments(argument);
            }
        }

        WebDriverManager.chromedriver().setup();
    }

    static boolean isServerRun() {
        return System.getenv("CI_RUN") != null;
    }

    static WebDriver createDriver() {
        return new ChromeDriver(chromeOptions);
    }

    static String getUserName() {
        return properties.getProperty(PROP_ADMIN_USERNAME);
    }

    static String getPassword() {
        return properties.getProperty(PROP_ADMIN_PAS);
    }
}
