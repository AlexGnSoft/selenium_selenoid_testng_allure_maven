package base;

import com.carespeak.core.config.Config;
import com.carespeak.core.config.ConfigProvider;
import com.carespeak.core.config.PropertyFileReader;
import com.carespeak.core.driver.factory.DriverFactory;
import com.carespeak.core.helper.IDataGenerator;
import com.carespeak.core.listener.ReportListener;
import com.carespeak.domain.steps.holders.SiteStepsHolder;
import com.epam.reportportal.testng.ReportPortalTestNGListener;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;

import java.io.File;

@Test(suiteName = "Smoke test suite")
@Listeners({ReportListener.class, ReportPortalTestNGListener.class})
public abstract class BaseTest implements IDataGenerator {

    protected Config config;
    protected SiteStepsHolder site;

    protected String user;
    protected String password;

    @BeforeTest
    public void beforeTest() {
        String env = System.getProperty("env", "demo");
        String url = "env" + File.separator + env + ".properties";
        ConfigProvider.init(new PropertyFileReader(), url);
    }

    @BeforeClass
    public void beforeClass() {
        config = ConfigProvider.provide();
        site = new SiteStepsHolder(config);
        user = config.get("data.user");
        password = config.get("data.pass");
    }

    @AfterTest
    public void afterTestClass() {
        RemoteWebDriver driver = DriverFactory.getDriver();
        if (driver != null) {
            driver.quit();
        }
    }
}
