package com.carespeak.domain.steps.reporter.step;

import com.carespeak.core.helper.IStepsReporter;
import com.epam.reportportal.listeners.ItemStatus;
import com.epam.reportportal.service.Launch;

import java.lang.reflect.Method;

public class ReportPortalStepReporter implements IStepsReporter {

    @Override
    public void onStepStart(Method stepMethod, Object[] args) {
        String prettyName = prettify(stepMethod.getName());
        String prettyArgs = getPrettyArgs(args);
        Launch.currentLaunch().getStepReporter().sendStep(prettyName + " " + prettyArgs);
    }

    @Override
    public void onStepFinished(Method stepMethod, Throwable t) {
        String prettyName = prettify(stepMethod.getName());
        if (t != null) {
            Launch.currentLaunch().getStepReporter().sendStep(ItemStatus.FAILED, prettyName);
        } else {
            Launch.currentLaunch().getStepReporter().finishPreviousStep();
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

    private static String getPrettyArgs(Object[] args) {
        if (args == null || args.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Object o : args) {
            sb.append(o);
            sb.append(",");
            sb.append(" ");
        }
        sb.append("]");
        //TODO: add last arguments pretty handling
        return sb.toString();
    }
}
