package utils.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class LogUtils {
    private static final String ERROR = "❌";
    private static final String SUCCESS = "✅";
    private static final String WARNING = "⚠️";
    private static final String EXCEPTION = "❗";

    private static final Logger logger = LogManager.getLogger("BaseTest");

    public static void logInfo(String message) {
        logger.info(message);
    }

    public static void logf(String str, Object... arr) {
       logInfo(String.format(str, arr));
    }

    public static void logError(String message) {
        logger.error(ERROR + message);
    }

    public static void logSuccess(String message) {
        logger.info(SUCCESS + message);
    }

    public static void logWarning(String message) {
        logger.info(WARNING + message);
    }

    public static void logException(String message) {
        logger.error(EXCEPTION + message);
    }

    public static void logFatal(String message) {
        logger.fatal(EXCEPTION + ERROR + EXCEPTION + message);
    }
}



