package com.carespeak.domain.steps.reporter;

import com.carespeak.core.helper.IStepsReporter;

import java.lang.reflect.Method;

public class SimpleReporter implements IStepsReporter {
    @Override
    public void onStepStart(Method stepMethod, Object[] args) {

    }

    @Override
    public void onStepFinished(Method stepMethod, Throwable t) {

    }
}
