package com.carespeak.core.helper;

import com.carespeak.domain.steps.BaseSteps;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public interface IStepsReporter {

    void onStepStart(Method stepMethod);

    void onStepFinished(Method stepMethod);

    @SuppressWarnings("unchecked")
    default <T> T createStepProxy(Class clazz) {
        Object stepsInstance = createInstance(clazz);
        InvocationHandler handler = getHandler(stepsInstance, clazz);
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), handler);
    }

    //TODO: remove tight connection with BaseSteps class
    default InvocationHandler getHandler(Object stepsObject, Class clazz) {
        return (proxy, method, args) -> {
            onStepStart(method);
            try {
                Object returnObj = method.invoke(stepsObject, args);
                if (returnObj instanceof BaseSteps) {
                    return createStepProxy(clazz);
                }
                onStepFinished(method);
                return returnObj;
            } catch (Throwable e) {
                e.printStackTrace();
            }
            onStepFinished(method);
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
