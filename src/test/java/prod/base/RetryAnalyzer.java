package prod.base;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    /**
     *  IRetryAnalyzer interface is used to retry flaky test
     */
    private int count = 0;
    private int maxCount = 1; // set your count to re-run test. If 1 - the test will be executed once again.


    @Override
    public boolean retry(ITestResult result) {
        if(count < maxCount) {
            count++;
            return true;
        }
        return false;
    }
}
