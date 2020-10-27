package base;

import com.carespeak.core.config.Config;
import com.carespeak.core.config.ConfigProvider;
import com.carespeak.core.config.PropertyFileReader;
import com.carespeak.core.driver.factory.DriverFactory;
import com.carespeak.core.helper.IDataGenerator;
import com.carespeak.domain.steps.holders.SiteStepsHolder;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.File;

public class BaseTest implements IDataGenerator {

    protected Config config;
    protected SiteStepsHolder site;

    protected String user;
    protected String password;

    @BeforeSuite
    public void beforeTestSuite() {
        String env = System.getProperty("env", "demo");
        String url = "env" + File.separator + env + ".properties";
        ConfigProvider.init(new PropertyFileReader(), url);
        config = ConfigProvider.provide();
        site = new SiteStepsHolder(config);
        user = config.get("data.user");
        password = config.get("data.pass");
    }

    @AfterSuite
    public void cleanUp() {
        RemoteWebDriver driver = DriverFactory.getDriver();
        if (driver != null) {
            driver.quit();
        }
    }
}
