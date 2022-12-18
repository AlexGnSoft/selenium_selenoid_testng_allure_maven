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
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import java.io.File;
import java.util.Arrays;

@Listeners({ExecutionTestOrderInterceptor.class, AllureReportListener.class, AllureTestNg.class})
public abstract class BaseTest implements IDataGenerator {

    protected static Config config;
    protected static PropertyFileReader propertyFileReader;
    protected SiteStepsHolder site;
    protected String user;
    protected String password;

    static {
        propertyFileReader = new PropertyFileReader();
        String envVariableValue = PropertyFileReader.getVariableValue("app_env");
        String env = System.getProperty("env", envVariableValue);
        String url = "env" + File.separator + env + ".properties";
        ConfigProvider.init(new PropertyFileReader(), url);
        config = ConfigProvider.provide();
        ElementActionsReporter.init(createDriverActionsReporter());
    }

    public BaseTest() {
        site = new SiteStepsHolder(config, createStepsReporter());
        user = config.get("data.user");
        password = config.get("data.pass");
        checkConfigurations();
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

    @BeforeSuite
    public void setUp(ITestContext context){
        //Using stream API we implement re-run failed tests to each test method
        //Arrays.stream(context.getAllTestMethods()).forEach(x->x.setRetryAnalyzerClass(RetryAnalyzer.class));
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
                File.separator + "data" +
                File.separator;
        return System.getProperty("resources.path", defaultPath);
    }

    private void checkConfigurations() {
        if (!config.get("driver.hub.baseUrl").isEmpty()) {
            if (StringUtils.isBlank(config.get("driver.hub.apiPort")) || StringUtils.isBlank(config.get("driver.hub.uiPort"))) {
                throw new IllegalArgumentException("You should specify:\n" +
                        "driver.hub.baseUrl" + "(http://myWebDriverHubAddress)\n" +
                        "driver.hub.apiPort" + "(some port for instance 4444)\n" +
                        "driver.hub.uiPort" + "(some port for instance 8081)\n" +
                        "if you want to execute tests on web driver hub,\notherwise leave this properties empty for local execution");
            }
        }
    }
}
