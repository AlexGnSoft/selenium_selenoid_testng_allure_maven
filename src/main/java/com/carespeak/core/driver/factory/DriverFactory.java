package com.carespeak.core.driver.factory;

import com.carespeak.core.config.Config;
import com.carespeak.core.config.ConfigProvider;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * DriverFactory can provide different webdriver instances based on configurations
 */
public class DriverFactory {

    //Thread safe storage for WebDrivers in case if code parallelism need
    private static ThreadLocal<RemoteWebDriver> drivers = new ThreadLocal<>();

    /**
     * Returns RemoteWebDriver instance based on command line arguments
     * or property file configuration for current thread.
     *
     * @return RemoteWebDriver instance for current thread.
     */
    public static synchronized RemoteWebDriver getDriver() {
        if (drivers.get() == null) {
            Config config = ConfigProvider.provide();
            String hubUrl = config.get("driver.hub.baseUrl");
            String driverName = config.get("driver.name");
            String driverVersion = config.get("driver.version");
            RemoteWebDriver driver;
            if (hubUrl != null && !hubUrl.isEmpty()) {
                hubUrl = hubUrl + ":" + config.get("driver.hub.apiPort") + "/wd/hub";
                driver = createRemoteWebDriver(driverName, hubUrl, driverVersion);
            } else {
                driver = createLocalWebDriver(driverName, driverVersion);
            }
            drivers.set(driver);
        }
        return drivers.get();
    }

    /**
     * Deregister driver from DriverFactory.
     * Makes sense to remove
     */
    public static synchronized void deregisterDriver() {
        drivers.remove();
    }

    /**
     * Setup webdriver binary and creates webdriver instance for provided driver name.
     *
     * @param driverName    - name of required web driver.
     * @param driverVersion - version of required webdriver. If null then latest version will be used.
     * @return RemoteWebDriver instance.
     */
    private static RemoteWebDriver createLocalWebDriver(String driverName, String driverVersion) {
        DriverManagerType driverType = getType(driverName);
        if (driverVersion != null && !driverVersion.isEmpty()) {
            WebDriverManager.getInstance(driverType).driverVersion(driverVersion).setup();
        } else {
            WebDriverManager.getInstance(driverType).setup();
        }
        RemoteWebDriver remoteWebDriver;
        switch (driverType) {
            case CHROME:
                remoteWebDriver = new ChromeDriver();
                break;
            case EDGE:
                remoteWebDriver = new EdgeDriver();
                break;
            default:
                throw new UnsupportedOperationException("Driver creation is not supported for type " + driverName);
        }
        remoteWebDriver.manage().window().maximize();
        return remoteWebDriver;
    }

    /**
     * Creates webdriver instance using selenium grid.
     *
     * @param driverName - name of required web driver.
     * @param hubUrl     - selenium hub url to interact with.
     * @param version    - string representation for version.
     * @return RemoteWebDriver instance.
     */
    private static RemoteWebDriver createRemoteWebDriver(String driverName, String hubUrl, String version) {
        DriverManagerType driverType = getType(driverName);

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", driverType.toString().toLowerCase());
        if (version != null && !version.isEmpty()) {
            capabilities.setCapability("browserVersion", version);
        }
        Map<String, Object> caps = new HashMap<>();
        caps.put("enableVNC", true);
        caps.put("enableVideo", Boolean.parseBoolean(ConfigProvider.provide().get("driver.recordVideo")));
        capabilities.setCapability("selenoid:options", caps);

        try {
            RemoteWebDriver remoteWebDriver = new RemoteWebDriver(
                    URI.create(hubUrl).toURL(),
                    capabilities
            );
            remoteWebDriver.setFileDetector(new LocalFileDetector());
            remoteWebDriver.manage().window().maximize();
            return remoteWebDriver;
        } catch (Throwable e) {
            throw new RuntimeException("Remote web driver session creation failed!", e);
        }
    }

    /**
     * Returns DriverManagerType for provided webdriver name.
     *
     * @param driverName - webdriver name.
     * @return DriverManagerType instance for provided webdriver name.
     */
    private static DriverManagerType getType(String driverName) {
        String driver = driverName.toLowerCase();
        if (driver.contains("chrome")) {
            return DriverManagerType.CHROME;
        }
        if (driver.contains("edge")) {
            return DriverManagerType.EDGE;
        }
        if (driver.contains("firefox")) {
            return DriverManagerType.FIREFOX;
        }
        if (driver.contains("safari")) {
            return DriverManagerType.SAFARI;
        }
        throw new IllegalArgumentException("Driver type is not recognized for " + driverName);
    }

}
