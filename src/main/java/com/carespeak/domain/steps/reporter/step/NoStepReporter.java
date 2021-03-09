package com.carespeak.domain.steps.reporter.step;

import com.carespeak.core.helper.IStepsReporter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class NoStepReporter implements IStepsReporter {

    @Override
    public void onStepStart(Method stepMethod, Object[] args) {

    }

    @Override
    public void onStepFinished(Method stepMethod, Throwable t) {

    }

    @Override
    public <T> T createStepProxy(Class clazz) {
        try {
            return (T) clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public InvocationHandler getHandler(Object stepsObject, Class clazz) {
        return null;
    }

    @Override
    public Object createInstance(Class clazz) {
        return null;
    }
}
