package utils.log;

import org.testng.ITestListener;
import org.testng.ITestResult;

public final class ExceptionListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        Throwable exception = result.getThrowable();
        LogUtils.logError("ERROR: caused by " + exception.getClass().getSimpleName());
        LogUtils.logException("EXCEPTION: " + exception.getMessage());
    }
}
