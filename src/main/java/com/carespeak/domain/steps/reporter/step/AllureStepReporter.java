package com.carespeak.domain.steps.reporter.step;

import com.carespeak.core.helper.IStepsReporter;
import com.carespeak.domain.steps.BaseSteps;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;
import java.util.concurrent.Callable;

import static io.qameta.allure.Allure.getLifecycle;

public class AllureStepReporter implements IStepsReporter {

    @Override
    public void onStepStart(Method stepMethod, Object[] args) {

    }

    @Override
    public void onStepFinished(Method stepMethod, Throwable t) {

    }

    private <T> T reportStep(String stepMessage, Callable<T> c) {
        final String uuid = UUID.randomUUID().toString();
        final StepResult result = new StepResult()
                .withName(stepMessage);
        getLifecycle().startStep(uuid, result);
        try {
            T res = c.call();
            getLifecycle().updateStep(uuid, s -> s.withStatus(Status.PASSED));
            return res;
        } catch (Throwable t) {
            if (t instanceof Error || t.getCause() instanceof Error) {
                getLifecycle().updateStep(uuid, s -> s.withStatus(Status.FAILED));
            } else {
                getLifecycle().updateStep(uuid, s -> s.withStatus(Status.BROKEN));
            }
            throw new RuntimeException(t.getCause());
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

    //TODO: remove tight connection with BaseSteps class
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
}
