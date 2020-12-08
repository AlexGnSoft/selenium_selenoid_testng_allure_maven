package com.carespeak.domain.steps.reporter;

import com.carespeak.core.helper.IStepsReporter;
import com.epam.reportportal.service.Launch;

import java.lang.reflect.Method;

public class ReportPortalStepReporter implements IStepsReporter {

    @Override
    public void onStepStart(Method stepMethod) {
        Launch.currentLaunch().getStepReporter().sendStep(stepMethod.getName());
    }

    @Override
    public void onStepFinished(Method stepMethod) {
        Launch.currentLaunch().getStepReporter().finishPreviousStep();
    }
}
