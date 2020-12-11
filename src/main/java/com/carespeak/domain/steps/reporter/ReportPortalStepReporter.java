package com.carespeak.domain.steps.reporter;

import com.carespeak.core.helper.IStepsReporter;
import com.epam.reportportal.service.Launch;

import java.lang.reflect.Method;

public class ReportPortalStepReporter implements IStepsReporter {

    @Override
    public void onStepStart(Method stepMethod) {
        String prettyName = prettify(stepMethod.getName());
        Launch.currentLaunch().getStepReporter().sendStep(prettyName);
    }

    @Override
    public void onStepFinished(Method stepMethod) {
        Launch.currentLaunch().getStepReporter().finishPreviousStep();
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
