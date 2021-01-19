package base;

import com.carespeak.core.config.Config;
import com.carespeak.core.config.ConfigProvider;
import com.carespeak.core.config.PropertyFileReader;
import com.carespeak.core.driver.factory.DriverFactory;
import com.carespeak.core.helper.IDataGenerator;
import com.carespeak.core.helper.IStepsReporter;
import com.carespeak.core.listener.ReportListener;
import com.carespeak.domain.steps.holders.SiteStepsHolder;
import com.epam.reportportal.testng.ReportPortalTestNGListener;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Listeners;

import java.io.File;

@Listeners({ReportListener.class, ReportPortalTestNGListener.class})
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
    }

    public BaseTest() {
        site = new SiteStepsHolder(config, createReporter());
        user = config.get("data.user");
        password = config.get("data.pass");
    }

    private IStepsReporter createReporter() {
        Class reporter;
        try {
            reporter = Class.forName(config.get("framework.reporter"));
            return (IStepsReporter) reporter.newInstance();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @AfterClass
    public void afterTest() {
        RemoteWebDriver driver = DriverFactory.getDriver();
        if (driver != null) {
            driver.quit();
        }
    }
}
