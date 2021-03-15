package com.carespeak.domain.steps.reporter.step;

import com.carespeak.core.config.Config;
import com.carespeak.core.config.ConfigProvider;
import com.carespeak.core.driver.factory.DriverFactory;
import com.carespeak.core.exception.WebDriverActionFailedException;
import com.carespeak.core.helper.IDataGenerator;
import com.carespeak.core.helper.IStepsReporter;
import com.carespeak.domain.steps.BaseSteps;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.Augmenter;

import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.Callable;

import static io.qameta.allure.Allure.getLifecycle;

public class AllureStepReporter implements IStepsReporter, IDataGenerator {

    @Override
    public void onStepStart(Method stepMethod, Object[] args) {

    }

    @Override
    public void onStepFinished(Method stepMethod, Throwable t) {

    }

    private <T> T reportStep(String stepMessage, Callable<T> c) {
        final String uuid = UUID.randomUUID().toString();
        final StepResult result = new StepResult()
                .setName(stepMessage);
        getLifecycle().startStep(uuid, result);
        try {
            T res = c.call();
            getLifecycle().updateStep(uuid, s -> s.setStatus(Status.PASSED));
            return res;
        } catch (Throwable t) {
            //Steps will contain Element actions, mostly like failure will happen in driver action,
            //and will be wrapped with RuntimeException, so to get rootcause we are searching rootcause recursively
            Throwable cause = t;
            while (true) {
                cause = cause.getCause();
                if (cause == null) {
                    break;
                }
                if (cause instanceof WebDriverActionFailedException) {
                    cause = cause.getCause();
                    break;
                }
            }
            if (cause instanceof Error) {
                getLifecycle().updateStep(uuid, s -> s.setStatus(Status.FAILED));
            } else {
                getLifecycle().updateStep(uuid, s -> s.setStatus(Status.BROKEN));
            }
            attachScreenshot("Screenshot");
            if (Boolean.parseBoolean(ConfigProvider.provide().get("driver.recordVideo"))) {
                attachVideo(DriverFactory.getDriver().getSessionId().toString());
            }
            throw new RuntimeException(cause);
        } finally {
            getLifecycle().stopStep(uuid);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T createStepProxy(Class clazz) {
        Object stepsInstance = createInstance(clazz);
        InvocationHandler handler = getHandler(stepsInstance, clazz);
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), handler);
    }

    public InvocationHandler getHandler(Object stepsObject, Class clazz) {
        return (proxy, method, args) -> {
            String prettyName = prettify(method.getName());
            Object returnObj = reportStep(prettyName, () -> method.invoke(stepsObject, args));
            if (returnObj instanceof BaseSteps) {
                return createStepProxy(clazz);
            }
            return returnObj;
        };
    }

    public Object createInstance(Class clazz) {
        Object stepsObj;
        try {
            stepsObj = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Failed to create steps implementation object for class: " + clazz);
        }
        return stepsObj;
    }

    @Attachment(value = "{name}", type = "image/png")
    private byte[] attachScreenshot(String name) {
        if (StringUtils.isBlank(ConfigProvider.provide().get("driver.hub.baseUrl"))) {
            return ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.BYTES);
        } else {
            return ((TakesScreenshot) new Augmenter().augment(DriverFactory.getDriver())).getScreenshotAs(OutputType.BYTES);
        }
    }

    private static String prettify(String s) {
        String str = s.replaceAll(
                String.format("%s|%s|%s",
                        "(?<=[A-Z])(?=[A-Z][a-z])",
                        "(?<=[^A-Z])(?=[A-Z])",
                        "(?<=[A-Za-z])(?=[^A-Za-z])"
                ),
                " "
        );
        String[] words = str.split(" ");
        StringBuilder builder = new StringBuilder();
        builder.append(capitalize(words[0]));
        for (int i = 1; i < words.length; i++) {
            builder.append(" ");
            builder.append(words[i].toLowerCase());
        }
        return builder.toString();
    }

    private static String capitalize(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    private static void attachVideo(String sessionId) {
        try {
            Config config = ConfigProvider.provide();
            String hubUrl = config.get("driver.hub.baseUrl") + ":" + config.get("driver.hub.uiPort");
            URL videoUrl = new URL( hubUrl + "/video/" + sessionId + ".mp4");
            InputStream is = getSelenoidVideo(videoUrl);
            Allure.addAttachment("Video", "video/mp4", is, "mp4");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static InputStream getSelenoidVideo(URL url) {
        int lastSize = 0;
        int exit = 2;
        for (int i = 0; i < 20; i++) {
            try {
                int size = Integer.parseInt(url.openConnection().getHeaderField("Content-Length"));
                if (size > lastSize) {
                    lastSize = size;
                    Thread.sleep(1500);
                } else if (size == lastSize) {
                    exit--;
                    Thread.sleep(1000);
                }
                if (exit < 0) {
                    return url.openStream();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
