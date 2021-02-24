package com.carespeak.core.helper;

import com.carespeak.domain.steps.BaseSteps;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.Callable;

public interface IStepsReporter {

    void onStepStart(Method stepMethod, Object[] args);

    void onStepFinished(Method stepMethod, Throwable t);

    <T> T reportStep(String stepMessage, Callable<T> c);

    @SuppressWarnings("unchecked")
    default <T> T createStepProxy(Class clazz) {
        Object stepsInstance = createInstance(clazz);
        InvocationHandler handler = getHandler(stepsInstance, clazz);
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), handler);
    }

    //TODO: remove tight connection with BaseSteps class
    default InvocationHandler getHandler(Object stepsObject, Class clazz) {
        return (proxy, method, args) -> {
            try {
                onStepStart(method, args);
                Object returnObj = method.invoke(stepsObject, args);
                if (returnObj instanceof BaseSteps) {
                    return createStepProxy(clazz);
                }
                onStepFinished(method, null);
                return returnObj;
            } catch (Throwable e) {
                e.printStackTrace();
                onStepFinished(method, e.getCause());
            }
            return null;
        };
    }

    default Object createInstance(Class clazz) {
        Object stepsObj;
        try {
            stepsObj = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Failed to create steps implementation object for class: " + clazz);
        }
        return stepsObj;
    }
}
