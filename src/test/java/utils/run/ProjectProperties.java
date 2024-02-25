package utils.run;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import utils.log.LogUtils;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;


public final class ProjectProperties {

    private static final String PROP_HOST = "host";
    private static final String PROP_PORT = "port";
    private static final String PROP_ADMIN_USERNAME = "admin.username";
    private static final String PROP_ADMIN_PAS = "admin.password";
    private static final String ENV_CHROME_OPTIONS = "CHROME_OPTIONS";
    private static final String ENV_APP_OPTIONS = "APP_OPTIONS";
    private static final String PROP_CHROME_OPTIONS = ENV_CHROME_OPTIONS.toLowerCase();

    private static Properties properties;

    static boolean isServerRun() {
        return System.getenv("CI_RUN") != null;
    }

    private static void initProperties() {
        if (properties == null) {
            properties = new Properties();
            if (isServerRun()) {
                properties.setProperty(PROP_CHROME_OPTIONS, System.getenv(ENV_CHROME_OPTIONS));

                if (System.getenv(ENV_APP_OPTIONS) != null) {
                    for (String option : System.getenv(ENV_APP_OPTIONS).split(";")) {
                        String[] webOptionArr = option.split("=");
                        properties.setProperty (webOptionArr[0], webOptionArr[1]);
                    }
                }
            } else {
                try {
                    InputStream inputStream = ProjectProperties.class.getClassLoader().getResourceAsStream("local.properties");
                    if (inputStream == null) {
                        System.out.println("ERROR: The \u001B[31mlocal.properties\u001B[0m file not found in src/test/resources/ directory.");
                        System.out.println("You need to create it from local.properties.TEMPLATE file.");
                    }
                    properties.load(inputStream);
                } catch (IOException e) {
                    LogUtils.logInfo("ERROR: The \u001B[31mlocal.properties\u001B[0m file not found.");
                    LogUtils.logInfo("You need to create it from config.properties.TEMPLATE file.");
                    System.exit(1);
                }
            }
        }
    }

    static String getUrl() {
        return String.format("http://%s:%s/",
                properties.getProperty(PROP_HOST),
                properties.getProperty(PROP_PORT));
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

    static final ChromeOptions chromeOptions;

    static {
        initProperties();

        chromeOptions = new ChromeOptions();
        String options = properties.getProperty(PROP_CHROME_OPTIONS);
        if (options != null) {
            for (String argument : options.split(";")) {
                chromeOptions.addArguments(argument);
            }
        }

        WebDriverManager.chromedriver().setup();
    }
}
