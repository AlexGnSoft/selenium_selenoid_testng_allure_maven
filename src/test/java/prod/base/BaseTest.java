package prod.base;

import com.carespeak.core.config.Config;
import com.carespeak.core.config.ConfigProvider;
import com.carespeak.core.config.PropertyFileReader;
import com.carespeak.core.driver.factory.DriverFactory;
import com.carespeak.core.driver.reporter.ElementActionsReporter;
import com.carespeak.core.driver.reporter.IElementInteractionsReporter;
import com.carespeak.core.helper.IDataGenerator;
import com.carespeak.core.helper.IStepsReporter;
import com.carespeak.core.listener.AllureReportListener;
import com.carespeak.core.listener.ExecutionTestOrderInterceptor;
import com.carespeak.domain.steps.holders.SiteStepsHolder;
import io.qameta.allure.testng.AllureTestNg;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

import java.io.File;

@Listeners({ExecutionTestOrderInterceptor.class, AllureReportListener.class, AllureTestNg.class})
public abstract class BaseTest implements IDataGenerator {

    protected static Config config;
    protected SiteStepsHolder site;

    protected String user;
    protected String password;

    static {
        String env = System.getProperty("env", "demo");
        String url = "env" + File.separator + env + ".properties";
        ConfigProvider.init(new PropertyFileReader(), url);
        config = ConfigProvider.provide();
        ElementActionsReporter.init(createDriverActionsReporter());
    }

    public BaseTest() {
        site = new SiteStepsHolder(config, createStepsReporter());
        user = config.get("data.user");
        password = config.get("data.pass");
    }

    private IStepsReporter createStepsReporter() {
        try {
            Class reporter = Class.forName(config.get("framework.reporter"));
            return (IStepsReporter) reporter.newInstance();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private static IElementInteractionsReporter createDriverActionsReporter() {
        try {
            Class reporter = Class.forName(config.get("driver.reporter"));
            return (IElementInteractionsReporter) reporter.newInstance();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeClass(alwaysRun = true)
    public void beforeClass() {
        site.loginSteps().openSite().loginAs(user, password);
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        RemoteWebDriver driver = DriverFactory.getDriver();
        if (driver != null) {
            driver.quit();
            DriverFactory.deregisterDriver();
        }
    }

    protected String getResourcesPath() {
        String defaultPath = System.getProperty("user.dir") +
                File.separator + "src" +
                File.separator + "main" +
                File.separator + "resources" +
                File.separator + "data";
        return System.getProperty("resources.path", defaultPath);
    }
}
